package model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class SAModel {
    private Random random = new Random();
    private SAModelState state = SAModelState.resting;
    private SAModelListener listener;
    private int numCity;
    private List<Double> mapX = new ArrayList<>();
    private List<Double> mapY = new ArrayList<>();
    private int[] nowRoute;
    private int[] bestRoute;
    private long step;
    private double maxTime;
    private double startTime;
    private double endTime;
    private double remainTime;
    private double nowTemp;
    private double firstTemp;
    private double nowDist;
    private double bestDist;

    public SAModel(SAModelListener listener) {
        this.listener = listener;
        random.setSeed(Calendar.getInstance().getTimeInMillis());
    }

    public void loadCSV(FileInputStream csvFile) {
        resetMap();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(csvFile));
            String line;
            while ((line = br.readLine()) != null) {
                String[] cityData = line.split(",");
                mapX.add(Double.parseDouble(cityData[0]));
                mapY.add(Double.parseDouble(cityData[1]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        numCity = mapY.size();
        listener.changedSAModel();
    }

    public void addCity(double x, double y) {
        if (state != SAModelState.resting) return;
        mapX.add(x);
        mapY.add(y);
        numCity++;
        listener.changedSAModel();
    }

    public SAModelState getSAModelState() {
        return state;
    }

    public void start() {
        state = SAModelState.working;
        listener.changedSAModel();

        //時刻の初期設定
        startTime = System.currentTimeMillis();
        endTime = startTime + maxTime;
        //街の数
        numCity = mapX.size();
        //街間の距離
        double[][] d = new double[numCity][numCity];
        for (int i = 0; i < numCity; i++)
            for (int j = 0; j < numCity; j++) {
                d[i][j] = Math.sqrt(Math.pow(mapX.get(j) - mapX.get(i), 2) + Math.pow(mapY.get(j) - mapY.get(i), 2));
            }
        //経路の初期設定
        nowRoute = new int[numCity];
        for (int i = 0; i < numCity; i++) nowRoute[i] = i;
        for (int i = 0; i < numCity; i++) {
            int rnd = (int) (random.nextDouble() * (double) numCity);
            int w = nowRoute[i];
            nowRoute[i] = nowRoute[rnd];
            nowRoute[rnd] = w;
        }
        bestRoute = new int[numCity];
        for (int k = 0; k < numCity; k++) bestRoute[k] = nowRoute[k];
        //総距離の初期設定
        nowDist = d[nowRoute[0]][nowRoute[numCity - 1]];
        for (int i = 0; i < numCity - 1; i++) nowDist += d[nowRoute[i]][nowRoute[i + 1]];
        bestDist = nowDist;
        //ステップ内で用いる変数
        step = 1;
        remainTime = endTime - System.currentTimeMillis();
        int newRoute[] = new int[numCity];
        double maxTime_inv=1.0/maxTime;
        int numCity_minus_1 = numCity - 1;
        int numCity_minus_2 = numCity - 2;
        new Thread(() -> {
            while (remainTime > 0) {
                //2Opt法によるルートの入れ替え候補の街を設定
                int swapStart;
                int swapEnd;
                synchronized (this){
                    swapStart=random.nextInt(numCity_minus_2)+1;
                    swapEnd=random.nextInt(numCity_minus_1-swapStart)+swapStart+1;
                }

                //距離の差と新距離の概算
                double minus_deltaDist;
                double newDist;
                synchronized (this) {
                    int swapStartOther=swapStart==0?numCity_minus_1:swapStart-1;
                    int swapEndOther=swapEnd==numCity_minus_1?0:swapEnd+1;
                    minus_deltaDist=
                            d[nowRoute[swapStartOther]][nowRoute[swapStart]]
                            +d[nowRoute[swapEndOther]][nowRoute[swapEnd]]
                            -d[nowRoute[swapEndOther]][nowRoute[swapStart]]
                            -d[nowRoute[swapStartOther]][nowRoute[swapEnd]];
                    newDist=nowDist-minus_deltaDist;
                }

                boolean atomMoved;

                //原子を移動するか
                synchronized (this) {
                    nowTemp=firstTemp*Math.pow(remainTime*maxTime_inv,6);
                    if (minus_deltaDist >= 0
                            || random.nextDouble() < Math.exp(minus_deltaDist / nowTemp )) {
                        //新ルートの構築
                        for (int k = 0; k < numCity; k++) newRoute[k] = nowRoute[k];
                        for (int k = 0; k <= swapEnd - swapStart; k++) {
                            newRoute[swapStart + k] = nowRoute[swapEnd - k];
                        }
                        for (int k = 0; k < numCity; k++) nowRoute[k] = newRoute[k];
                        //新距離の厳密計算
                        newDist = d[newRoute[0]][newRoute[numCity_minus_1]];
                        for (int k = 0; k < numCity_minus_1; k++) newDist += d[newRoute[k]][newRoute[k + 1]];
                        nowDist=newDist;
                        atomMoved=true;
                    }else{
                        atomMoved=false;
                    }
                }

                //最適解
                synchronized (this) {
                    if (bestDist > newDist) {
                        if(!atomMoved) {
                            //新ルートの構築
                            for (int k = 0; k < numCity; k++) newRoute[k] = nowRoute[k];
                            for (int k = 0; k <= swapEnd - swapStart; k++) {
                                newRoute[swapStart + k] = nowRoute[swapEnd - k];
                            }
                            //新距離の厳密計算
                            newDist = d[newRoute[0]][newRoute[numCity_minus_1]];
                            for (int k = 0; k < numCity_minus_1; k++) newDist += d[newRoute[k]][newRoute[k + 1]];
                        }
                        //新ルートと新距離の記録
                        for (int k = 0; k < numCity; k++) bestRoute[k] = newRoute[k];
                        bestDist=newDist;
                    }
                }
                synchronized (this) {
                    step++;
                    remainTime = endTime - System.currentTimeMillis();
                }

            }

            synchronized (this) {
                nowRoute = bestRoute;
                nowDist = bestDist;
            }
            state = SAModelState.resting;
            listener.changedSAModel();
        }).start();
    }

    public void setMaxTime(double t) {
        maxTime = t * 1000;
    }

    public void setFirstTemp(double t) {
        firstTemp = t;
    }

    public void resetMap() {
        nowRoute = null;
        mapX = new ArrayList<>();
        mapY = new ArrayList<>();
        numCity = 0;
        listener.changedSAModel();
    }

    public synchronized int getNumCity() {
        return numCity;
    }

    public synchronized double getNowCost() {
        return nowDist;
    }

    public synchronized double getBestCost() {
        return bestDist;
    }

    public synchronized long getNowStep() {
        return step;
    }

    public synchronized double getNowTemp() {
        return nowTemp;
    }

    public synchronized double getRemainTime() {
        return remainTime;
    }

    public List<Double> getMapX() {
        return mapX;
    }

    public List<Double> getMapY() {
        return mapY;
    }

    public synchronized int[] getRoute() {
        return nowRoute;
    }

    public enum SAModelState {
        working,
        resting
    }
}
