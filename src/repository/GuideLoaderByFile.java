package repository;

import model.guide.Guide;
import model.guide.GuideContent;
import model.guide.GuideLoader;

import java.io.*;

public class GuideLoaderByFile implements GuideLoader {
    @Override
    public Guide execute() {
        Guide out=new Guide();
        String targetDirectoryPath
                =new StringBuilder().append(CurrentPath.getCurrentPath()).append("\\values\\guide_data").toString();
        File targetDirectory=new File(targetDirectoryPath);
        for(File textFile:targetDirectory.listFiles()){

            try {
                BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(textFile),"utf-8"));

                String title=reader.readLine();
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
