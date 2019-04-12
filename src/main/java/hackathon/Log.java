package hackathon;

public class Log {
    private String stockname;
    private boolean buy;
    private int numstocks;
    private double price;
    private double curr_portfolio_value;

    public Log(String stockname, boolean buy, int numstocks, double price, double curr_portfolio_value) {
        this.stockname = stockname;
        this.buy = buy;
        this.numstocks = numstocks;
        this.price = price;
        this.curr_portfolio_value = curr_portfolio_value;
    }

    public String getStockname() {
        return stockname;
    }

    public boolean isBuy() {
        return buy;
    }

    public int getNumstocks() {
        return numstocks;
    }

    public double getPrice() {
        return price;
    }

    public double getCurr_portfolio_value() {
        return curr_portfolio_value;
    }
}
