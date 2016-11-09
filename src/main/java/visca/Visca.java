package visca;

import jssc.SerialPort;
import jssc.SerialPortException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by dawid on 09.11.16.
 */
@RestController
public class Visca {

    static String commName = "COM1";
    static SerialPort serialPort;// = new SerialPort(commName);
    static private byte speed1 = 3;
    static private byte speed2 = 3;
    static ArrayList<String> macros;
    static private String response;

    public Visca(){
        Scanner in = new Scanner(System.in);

        serialPort = new SerialPort(commName);
        commName = "COM1";
        macros = new ArrayList<String>();
    }

    public static byte[] readResponseHelp(SerialPort serialPort){
        try {
           return ResponseReader.readResponse(serialPort);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String bytesToString(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        byte[] arrby = bytes;
        int n = arrby.length;
        int n2 = 0;
        while (n2<n){
            byte b = arrby[n2];
            sb.append(String.format("%02X ", Byte.valueOf(b)));
            ++n2;
        }
        return sb.toString();
    }

    @RequestMapping("/")
    public String index(){
        return "Welcome";
    }

    @RequestMapping("/home")
    public String home(){
        try {
            Home.move(serialPort);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        byte[] respons = readResponseHelp(serialPort);
        response = bytesToString(respons);
        return "{\"res\": \"" + response + "\"}";
    }

    @RequestMapping(value = "/up")
    @ResponseBody
    public String up(){
        try {
            MoveUp.move(serialPort, speed1, speed2);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        byte[] respons = readResponseHelp(serialPort);
        response = bytesToString(respons);
        return "{\"res\": \"" + response + "\"}";
//        try {
//            return new JSONObject("{response: "+ bytesToString(response) + "}");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        try {
//            return new JSONObject("a:a");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return null;
    }

    @RequestMapping("/down")
    public String down(){
        try {
            MoveDown.move(serialPort, speed1, speed2);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        byte[] respons = readResponseHelp(serialPort);
        response = bytesToString(respons);
        return "{\"res\": \"" + response + "\"}";
    }

    @RequestMapping("/left")
    public String left(){
        try {
            MoveLeft.move(serialPort, speed1, speed2);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        byte[] respons = readResponseHelp(serialPort);
        response = bytesToString(respons);
        return "{\"res\": \"" + response + "\"}";
    }

    @RequestMapping("/right")
    public String right(){
        try {
            MoveRight.move(serialPort, speed1, speed2);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        byte[] respons = readResponseHelp(serialPort);
        response = bytesToString(respons);
        return "{\"res\": \"" + response + "\"}";
    }

    @RequestMapping("/tele")
    public String tele(){
        try {
            ZoomTele.move(serialPort);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        byte[] respons = readResponseHelp(serialPort);
        response = bytesToString(respons);
        return "{\"res\": \"" + response + "\"}";
    }

    @RequestMapping("/wide")
    public String wide(){
        try {
            ZoomWide.move(serialPort);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        byte[] respons = readResponseHelp(serialPort);
        response = bytesToString(respons);
        return "{\"res\": \"" + response + "\"}";
    }


    @RequestMapping("/speed/sp1={sp1}/sp2={sp2}")
    public String wide(@PathVariable("sp1") byte sp1, @PathVariable("sp2") byte sp2){
             speed1 = sp1;
            speed2 = sp2;
        return "{\"res\": \"" + "Speed " + sp1 + " " + sp2 + "\"}";
    }

}
