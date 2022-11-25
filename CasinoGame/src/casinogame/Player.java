
package casinogame;

public class Player {
    protected String name;
    protected String userName;
    protected String password;
    protected String email;
    protected double money;

    public Player(String name, String userName, String password, String email, double money) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.money = money;
    }
    
    
    public String toString(){
        return name+", "+userName+", "+password+", "+email+", "+money;
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public double getMoney() {
        return money;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMoney(double money) {
        this.money = money;
    }
    
    
    
}
