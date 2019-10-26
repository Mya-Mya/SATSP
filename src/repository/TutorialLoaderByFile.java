package repository;

import model.Tutorial;
import model.TutorialContent;
import model.TutorialLoader;

import java.io.*;

public class TutorialLoaderByFile implements TutorialLoader {
    @Override
    public Tutorial execute() {
        Tutorial out=new Tutorial();
        String jarPath=System.getProperty("java.class.path");
        String dirPath=jarPath.substring(0,jarPath.lastIndexOf(File.separator)+1);


        for(int i=0;i<=19;i++){
            try {
                StringBuilder fileNameBuilder=new StringBuilder();
                fileNameBuilder.append(dirPath).append("\\values\\tutorial_data\\").append(i).append(".txt");
                File file =new File(fileNameBuilder.toString());
                StringBuilder contentTextBuilder=new StringBuilder();
                FileReader reader1=new FileReader(file);
                BufferedReader reader2=new BufferedReader(reader1);
                String line;
                while((line=reader2.readLine())!=null){
                    if(!contentTextBuilder.toString().equals("")){
                        contentTextBuilder.append("\n");
                    }
                    contentTextBuilder.append(line);
                }

                String tutorialContent=contentTextBuilder.toString();
                //tutorialContent=tutorialContent.substring(1);
                out.addContent(new TutorialContent(tutorialContent));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return out;
    }
}
