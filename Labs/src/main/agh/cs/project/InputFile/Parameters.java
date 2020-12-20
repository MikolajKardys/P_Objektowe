package agh.cs.project.InputFile;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;

import java.io.FileReader;


public class Parameters {

    String [] parametrs;
    String [] keyWords;

    public Parameters() {
        parametrs = new String [7];
        keyWords = new String [] {"width", "height", "number", "start", "moving", "eating", "ratio"};
    }

    public String [] getParameters(){
        this.reloadParameters();
        return parametrs;
    }

    private void reloadParameters() {
        for (String parm : parametrs){
            parm = "";
        }

        JSONParser parser = new JSONParser();
        try {
            String inputPath = System.getProperty("user.dir") + "/src/main/agh/cs/project/InputFile/parameters.json";

            JSONObject obj = (JSONObject) parser.parse(new FileReader(inputPath));

            String [] args = obj.toJSONString().split(",");

            for (String arg : args){
                arg = arg.replace("{", "");
                arg = arg.replace("}", "");
                String [] values = arg.split(":");

                for (int i = 0; i < 7; i++){
                    if (values[0].contains(keyWords[i])){
                        parametrs[i] = values[1];
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
