package com.bluealien99.simplecalc;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

class Output {

    private static String errorMsg = "ERROR";
    private static String overflowMsg = "OVERFLOW";
    private static String minus = "−";
    private static int precision = 4;
    private double fir;
    private String firS = "";
    private double sec;
    private String secS = "";
    private boolean next = false;
    private String action = "";
    private TextView out;
    private String[] keypad;
    private ArrayList<String> history;
    private ArrayAdapter<String> hisListAdapter;
    private ListView hisList;
    private Context context;

    //@SuppressWarnings("unchecked")
    Output(TextView out, String[] keypad, ArrayList<String> history, ArrayAdapter<String> hisListAdaper, ListView hisList, Context context) {
        this.out = out;
        this.keypad = keypad;
        this.history = history;
        this.hisListAdapter = hisListAdaper;
        this.hisList = hisList;
        this.context = context;
    }

    void inputNum(long id) {
        isError();
        long digit = id + 1 + (1 - id / 3) * 6;
        if (id == 10) {
            if (((firS.equals("0") || firS.equals(minus + "0")) && !next)
                    || secS.equals("0") || secS.equals(minus + "0")) return;
            else digit = 0;
        } else if (id == 11) {
            equality(11);
            return;
        } else if (id == 9) {
            comma();
            return;
        }
        if (!next) {
            if (firS.equals("0")) firS = "";
            else if (firS.equals(minus + "0")) firS = minus;
            firS += digit;
        } else {
            if (secS.equals("0")) secS = "";
            else if (secS.equals(minus + "0")) secS = minus;
            secS += digit;
        }
        update();
    }

    void inputKey(long id) {
        isError();
        boolean actionTaken = false;
        if (id == 5) {
            if (firS.isEmpty() || firS.equals("0")) {
                firS = minus + "0";
                actionTaken = true;
            } else if (firS.equals(minus + "0")) {
                firS = "0";
                actionTaken = true;
            } else if (secS.isEmpty() && next) {
                secS = minus;
                actionTaken = true;
            } else if (secS.equals(minus) && next) {
                secS = "";
                actionTaken = true;
            }
            updateSoft();
        }
        if (!actionTaken && !firS.isEmpty() && !firS.equals(minus) && secS.isEmpty()) {
            action = " " + keypad[(int) id] + " ";
            updateSoft();
            next = true;
        } else equality(id);
    }

    void clear() {
        if (isError()) return;
        if (!next) {
            if (firS.length() >= 2) {
                String t = firS.substring(firS.length() - 1);
                firS = firS.substring(0, firS.length() - 1);
                if (firS.equals(minus)) {
                    if (t.equals("0")) firS = "0";
                    else firS += "0";
                    updateSoft();
                } else update();
            } else {
                firS = "0";
                updateSoft();
            }
        } else if (secS.length() >= 2) {
            secS = secS.substring(0, secS.length() - 1);
            if (secS.equals(minus)) updateSoft();
            else update();
        } else if (secS.length() == 1) {
            secS = "";
            updateSoft();
        } else {
            action = "";
            next = false;
            update();
        }
    }

    private void clearHard() {
        out.setTextColor(ContextCompat.getColor(context, R.color.colorAccent3));
        firS = "0";
        secS = "";
        action = "";
        next = false;
        updateSoft();
    }

    void clearHardBtn() {
        clearHard();
        history.clear();
        hisListAdapter.notifyDataSetChanged();
    }

    private void equality(long id) {
        if (firS.isEmpty() || secS.isEmpty() || firS.equals(minus) || secS.equals(minus)) return;
        if (action.equals(" ÷ ") && sec == 0) {
            out.setText(errorMsg);
            out.setTextColor(ContextCompat.getColor(context, R.color.error));
            return;
        }
        double sol;
        String his = firS + action + secS + " = ";
        switch (action) {
            case " + ":
                sol = fir + sec;
                break;
            case " − ":
                sol = fir - sec;
                break;
            case " × ":
                sol = fir * sec;
                break;
            case " ÷ ":
                sol = fir / sec;
                break;
            case " √ ":
                sol = Math.pow(sec, 1.0 / fir);
                break;
            case " ^ ":
                sol = Math.pow(fir, sec);
                break;
            default:
                sol = 0;
        }
        clearHard();
        if (sol > Integer.MAX_VALUE || sol < Integer.MIN_VALUE) {
            out.setText(overflowMsg);
            out.setTextColor(ContextCompat.getColor(context, R.color.error));
            return;
        }
        if (Math.IEEEremainder(sol, 1.0) == 0) firS = String.valueOf((int) sol);
        else {
            sol = Math.round(sol * Math.pow(10, precision));
            sol /= Math.pow(10, precision);
            firS = String.valueOf(sol);
        }
        if (firS.substring(0, 1).equals("-")) firS = minus + firS.substring(1);
        update();
        his += firS;
        history.add(his);
        hisListAdapter.notifyDataSetChanged();
        hisList.smoothScrollToPosition(history.size() - 1);
        if (id != 11) inputKey(id);
    }

    private void comma() {
        if ((!next && firS.contains(".")) || secS.contains(".")) return;
        if (!next) {
            if (firS.isEmpty() || firS.equals(minus)) inputNum(10);
            firS += ".";
        } else {
            if (secS.isEmpty() || secS.equals(minus)) inputNum(10);
            secS += ".";
        }
        update();
    }

    private void update() {
        if (!next) {
            String temp = firS;
            if (firS.substring(0, 1).equals(minus)) temp = "-" + firS.substring(1);
            fir = Double.parseDouble(temp);
        } else {
            String temp = secS;
            if (secS.substring(0, 1).equals(minus)) temp = "-" + secS.substring(1);
            sec = Double.parseDouble(temp);
        }
        updateSoft();
    }

    private void updateSoft() {
        out.setText(firS + action + secS);
    }

    private boolean isError() {
        if (errorMsg.equals(out.getText().toString()) || overflowMsg.equals(out.getText().toString())) {
            clearHard();
            return true;
        } else return false;
    }
}
