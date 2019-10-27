package repository;

import model.guide.Guide;
import model.guide.GuideContent;
import model.guide.GuideLoader;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Comparator;

public class GuideLoaderByFile implements GuideLoader {
    @Override
    public Guide execute() {
        Guide out=new Guide();
        String targetDirectoryPath
                =new StringBuilder().append(CurrentPath.getCurrentPath()).append("\\values\\guide_data").toString();
        File targetDirectory=new File(targetDirectoryPath);

        File[]textFileList= targetDirectory.listFiles();
        Arrays.sort(textFileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                String name1=o1.getName();
                String name2=o2.getName();
                int i1=Integer.parseInt(name1.substring(0,name1.lastIndexOf('.')));
                int i2=Integer.parseInt(name2.substring(0,name2.lastIndexOf('.')));
                return i1-i2;
            }
        });
        for(File textFile:textFileList){

            try {
                BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(textFile),"utf-8"));

                String title=reader.readLine();
                if(title.charAt(0)=='\uFEFF'){
                    title=title.substring(1);
                }
                String content=reader.readLine();

                out.addContent(new GuideContent(title,content));


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return out;
    }
}
