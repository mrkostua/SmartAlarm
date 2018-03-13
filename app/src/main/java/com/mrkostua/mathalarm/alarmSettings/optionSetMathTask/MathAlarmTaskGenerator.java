package com.mrkostua.mathalarm.alarmSettings.optionSetMathTask;

import com.mrkostua.mathalarm.ShowLogsOld;

import java.util.Random;

/**
 * @author Konstantyn
 */
public class MathAlarmTaskGenerator {

    private Random random = new Random();

    private int result,number1,number2,number3, powValue;
    private String mathSymbol,mathSymbol1,mathSymbol2;

    /**
     * symbol: 1 (sub), 2 (add), 3 (mul), 4 (div)
     * complexity: 0-(4 symbols), 1-(2 symbols)
     */
    private int GenerateRandomMathSymbol(int complexityLevel){
        int[] randomCharacterEasy = new int[2];
        //for  easy complexity only 4 sign
        randomCharacterEasy[0] = random.nextInt(5 - 1) + 1;
        //for  medium complexity 2 sign
        randomCharacterEasy[1] = random.nextInt(3 - 1) + 1;

        switch (randomCharacterEasy[complexityLevel]) {
            case 1: { // -
                mathSymbol = "-";
               return 1;
            }
            case 2: { // +
                mathSymbol = "+";
                return 2;
            }
            case 3: { // *
                mathSymbol = "*";
              return 3;
            }
            case 4: { // /
                mathSymbol = "/";
                return 4;
            }
        }
        return 0;
    }

    private int GenerateRandomNumber(int max, int min){
        return random.nextInt((max - min) -1) +min;
    }

    private int CountResultOfEquation(int number1, int number2, int symbol){
        switch (symbol) {
            case 1: { // -
                return number1 - number2;
            }
            case 2: { // +
                return number1 + number2;
            }
            case 3: { // *
                if(number1 ==1 || number2==1){
                    number1 = GenerateRandomNumber(99,2);
                    number2 = GenerateRandomNumber(9,2);
                    this.number1 = number1;
                    this.number2=number2;
                }
                return number1 * number2;
            }
            case 4: { // /
                boolean acceptableEquation = true;
                int r=0;
                while(acceptableEquation) {
                    acceptableEquation = false;
                     r = number1 / number2;

                    if (r == 1 || r==0) {
                        if(ShowLogsOld.LOG_STATUS) ShowLogsOld.i("r == 1 || r==0 number1=" + number1 +" number2=" +number2);
                        acceptableEquation = true;
                        number1 = GenerateRandomNumber(99,2);
                        number2 = GenerateRandomNumber(9,2);
                        this.number1 = number1;
                        this.number2= number2;
                    }
                    if (number1 > number2) {
                        this.number1 = r * number2;
                        if(ShowLogsOld.LOG_STATUS) ShowLogsOld.i("number1 > number2 number1=" + this.number1 +" number2=" +number2);
                    }
                }
                return r;
            }
            default:
                if(ShowLogsOld.LOG_STATUS) ShowLogsOld.i("CountResultOfEquation error symbol is " + symbol);
                break;
        }
        return 0;
    }


    public void GeneratorEquation (int complexity){
    int symbol1,symbol2;
        switch (complexity){
            case 0:{//easy
                number1 = GenerateRandomNumber(99,1);
                number2 = GenerateRandomNumber(9,1);
                symbol1 = GenerateRandomMathSymbol(0);
                mathSymbol1 = mathSymbol;
                result = CountResultOfEquation(number1,number2,symbol1);
            }break;
            case 1:{//medium
                number1 = GenerateRandomNumber(99,1);
                symbol1 = GenerateRandomMathSymbol(0);
                mathSymbol1 = mathSymbol;
                number2 = GenerateRandomNumber(9,1);
                result = CountResultOfEquation(number1,number2,symbol1);

                symbol2 = GenerateRandomMathSymbol(1);
                mathSymbol2 = mathSymbol;
                number3 = GenerateRandomNumber(99,1);
                result = CountResultOfEquation(result,number3,symbol2);
            }break;

            case 2:{//hard
                number1 = GenerateRandomNumber(99,1);
                symbol1 = GenerateRandomMathSymbol(0);
                mathSymbol1 = mathSymbol;
                number2 = GenerateRandomNumber(9,1);
                result = CountResultOfEquation(number1,number2,symbol1);

                symbol2= GenerateRandomMathSymbol(1);
                mathSymbol2 = mathSymbol;
                number3 = GenerateRandomNumber(9,1);
                powValue = GenerateRandomNumber(4,2);
                result = CountResultOfEquation(result,(int) Math.pow(number3, powValue),symbol2);

            }break;
            case 3:{//unbelievably hard

            }break;

            default:
                if(ShowLogsOld.LOG_STATUS) ShowLogsOld.i("GeneratorEquation error complexity > 4");
            break;
        }
    }

    public int Number1Getter(){
        return number1;
    }
    public int Number2Getter(){
        return number2;
    }
    public int Number3Getter(){
        return number3;
    }
    public String SymbolGetter(){
        return mathSymbol1;
    }
    public String Symbol2Getter(){
        return mathSymbol2;
    }
    public int ResultGetter(){
        return result;
    }
    public int PowerNumberGetter(){return powValue;}
}
