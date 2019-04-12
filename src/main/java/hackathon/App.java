package hackathon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class App {

    public static void main(String[] args) {
        Portfolio portfolio = new Portfolio(1000);
        JFrame frame = new JFrame("portfolio manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1800, 800);
        frame.setLayout(new GridLayout(2, 1));
        JPanel textPanel = new JPanel(new GridLayout(4, 50));
        JPanel buttonPanel = new JPanel();
        JTextField textField = new JTextField(40);

        JTextField resultField = new JTextField(50);
        resultField.setEditable(false);
        JTextField helpField = new JTextField(50);
        helpField.setEditable(false);

        JTextField helpField2 = new JTextField(50);
        helpField2.setEditable(false);

        helpField.setText(" type the symbol for the stock and separate each symbol by a single space.");

        //       textField.setSize(400,100);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                e.getSource();
                String input = textField.getText();
                if (input == null || input.length() == 0) {
                    return;
                }
                String[] lookupstocks = input.split(" ");
                StockDataProvider search = new StockDataProvider();
                StockData[] data = search.getStockData(lookupstocks);
                if (data != null) {

                    StringBuilder sb = new StringBuilder();
                    for (StockData finder : data) {
                        sb.append(finder.getSymbol() + ": $" + finder.getAskPrice() + ", ");
                    }

                    resultField.setText(sb.toString());
                } else {
                    resultField.setText("Stock Data not found");
                }
                resultField.update(resultField.getGraphics());
            }
        });

        JButton buyButton = new JButton("Buy");
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                e.getSource();
                String input = textField.getText();
                if (input == null || input.length() == 0) {
                    return;
                }
                String[] lookupstocks = input.split(" ");
                StockDataProvider search = new StockDataProvider();
                StockData[] data = search.getStockData(lookupstocks);
                if (data != null) {
                    StringBuilder sb = new StringBuilder();
                    for (StockData finder : data) {
                        sb.append(finder.getSymbol() + ": $" + finder.getAskPrice() + ", ");
                    }
                    resultField.setText(sb.toString());
                } else {
                    resultField.setText("Stock Data not found");
                }
                input = JOptionPane.showInputDialog("How many of each do you want? Separate each quantity by a space.");
                String[] qtyBuy = input.split(" ");
                int[] intqtyBuy = new int[qtyBuy.length];
                for (int i = 0; i < qtyBuy.length; i++) {
                    intqtyBuy[i] = Integer.parseInt(qtyBuy[i]);
                }
                for (int i = 0; i < data.length; i++) {
                    portfolio.add_stocks(intqtyBuy[i], data[i]);
                    portfolio.showPortfolio();
                }
            }
        });

        JButton sellButton = new JButton("Sell");
        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                e.getSource();
                String input = textField.getText();
                if (input == null || input.length() == 0) {
                    return;
                }
                String[] lookupstocks = input.split(" ");
                StockDataProvider search = new StockDataProvider();
                StockData[] data = search.getStockData(lookupstocks);
                if (data != null) {
                    StringBuilder sb = new StringBuilder();
                    for (StockData finder : data) {
                        sb.append(finder.getSymbol() + ": $" + finder.getAskPrice() + ", ");
                    }
                    resultField.setText(sb.toString());
                } else {
                    resultField.setText("Stock Data not found");
                }
                input = JOptionPane.showInputDialog("How many of each do you want to sell? Separate each quantity by a space.");
                String[] qtyBuy = input.split(" ");
                int[] intqtyBuy = new int[qtyBuy.length];
                for (int i = 0; i < qtyBuy.length; i++) {
                    intqtyBuy[i] = Integer.parseInt(qtyBuy[i]);
                }
                for (int i = 0; i < data.length; i++) {
                    boolean noError = portfolio.remove_Stocks(intqtyBuy[i], data[i]);
                    if (!noError) {
                        JOptionPane.showMessageDialog(null, "You do not have enough of that stock. Please enter a smaller number to buy.", "Buy Error", JOptionPane.INFORMATION_MESSAGE);
                    }
                    portfolio.showPortfolio();
                }
            }
        });

        JButton portfolioButton = new JButton("Portfolio");
        portfolioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultField.setText(portfolio.getPortfolio());
                resultField.update(resultField.getGraphics());
            }
        });

        JButton logButton = new JButton("Daily Log");
        logButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultField.setText(portfolio.getDailyLog().getDailyLog());
                resultField.update(resultField.getGraphics());
            }
        });


        frame.add(textPanel);
        frame.add(buttonPanel);
        textPanel.add(helpField);
        textPanel.add(textField);
        buttonPanel.add(searchButton);
        buttonPanel.add(buyButton);
        buttonPanel.add(sellButton);
        buttonPanel.add(portfolioButton);
        buttonPanel.add(logButton);
        textPanel.add(resultField);
        frame.setVisible(true);


    }
}