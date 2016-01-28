package ru.stupnikov.application.processor;

import java.util.ArrayList;

import ru.stupnikov.application.data.Valuta;

/**
 * Created by rodion on 25.01.16.
 */
public  class Converter {



    public static double convertToValuta(ArrayList<Valuta> listValuta, String transmitterValuta, double value, String senderValuta){

        double dTransmitterValue = -1;
        double dSenderValue = -1;

        for (Valuta v :listValuta){
            if (transmitterValuta.equals(v.name)){
                dTransmitterValue = v.valueRUB;
            }
        }
        if(dTransmitterValue == -1) return -1;
        for (Valuta v: listValuta){
            if(senderValuta.equals(v.name)){
                dSenderValue = v.valueRUB;
            }
        }
        if(dSenderValue == -1) return -1;

        return (value*dTransmitterValue)/dSenderValue;
    }

    public static double convertToValuta (Double transmitterValue, double value, Double senderValue){

        return  transmitterValue*value/senderValue;
    }
}
