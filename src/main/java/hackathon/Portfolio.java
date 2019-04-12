package hackathon;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class Portfolio {
    private double value;
    private Map<String, Integer> portfolio_stocks;
    private DailyLog dailyLog;

    Portfolio(double value) {
        this.value = value*1.00;
        portfolio_stocks = new HashMap<String, Integer>();
        dailyLog = new DailyLog();
    }

    public double getValue() {
        return value;
    }

    public void add_stocks(int addCount, StockData data) {
        double total_price = addCount * data.getAskPrice();
        String stockName = data.getSymbol();
        int prevNum = portfolio_stocks.containsKey(stockName) ? portfolio_stocks.get(stockName) : 0;
        int newCount = prevNum + addCount;
        portfolio_stocks.put(stockName, newCount);
        value -= total_price;
        dailyLog.add_entry(stockName, true, addCount, total_price, value);
    }

    public boolean containsKey(String stockName) {
        return portfolio_stocks.containsKey(stockName);
    }

    public boolean remove_Stocks(int subCount, StockData data) {
        double total_price = subCount * data.getAskPrice();
        String stockName = data.getSymbol();
        int prevNum = portfolio_stocks.containsKey(stockName) ? portfolio_stocks.get(stockName) : 0;
        int newCount = prevNum - subCount;
        if (prevNum < subCount) {
            return false;
        } else if (newCount == 0) {
            portfolio_stocks.remove(stockName);
        } else
            portfolio_stocks.put(stockName, newCount);
        value += total_price;
        dailyLog.add_entry(stockName, false, subCount, total_price, value);
        return true;
    }

    public void showPortfolio() {
        String port = getPortfolio();
        JOptionPane.showMessageDialog(null, port, "My Portfolio", JOptionPane.INFORMATION_MESSAGE);
    }

    public String getPortfolio() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : portfolio_stocks.entrySet()) {
            // Print each entry line by line
            sb.append(entry.getKey());
            sb.append("     ");
            sb.append(entry.getValue());
            sb.append("     ");
            sb.append("\n");
        }
        sb.append("Capital: ");
        sb.append(value);

        return sb.toString();

    }

    public DailyLog getDailyLog(){
        return dailyLog;
    }
}
