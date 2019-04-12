package hackathon;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class DailyLog {
    private List<Log> activities;

    DailyLog() {
        activities = new LinkedList<Log>();
    }

    public void add_entry(String stockname, boolean buy, int numstocks, double price, double curr_portfolio_value) {
        Log newLog = new Log(stockname, buy, numstocks, price, curr_portfolio_value);
        activities.add(newLog);
    }

    public void showPortfolio() {
        String port = getDailyLog();
        JOptionPane.showMessageDialog(null, port, "My Daily Log", JOptionPane.INFORMATION_MESSAGE);
    }

    public String getDailyLog() {
        StringBuilder sb = new StringBuilder();
        for (Log entry : activities) {
            // Print each entry line by line
            sb.append(entry.getStockname());
            sb.append("     ");
            if (entry.isBuy()) {
                sb.append("Buy     ");
            } else {
                sb.append("Sell     ");
            }
            sb.append(entry.getNumstocks());
            sb.append("     ");
            sb.append(entry.getPrice());
            sb.append("     Capital: ");
            sb.append(entry.getCurr_portfolio_value());
            sb.append("\n  -------  ");
        }
        return sb.toString();
    }
}
