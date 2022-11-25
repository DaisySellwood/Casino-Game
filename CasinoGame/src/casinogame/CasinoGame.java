package casinogame;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class CasinoGame {

    public static Scanner input = new Scanner(System.in);
    public static Random rand = new Random();
    public static ArrayList<Player> playerList = new ArrayList<>();
    public static String[] cardList = {"Ace (1)", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack (11)", "Queen (12)", "King (13)"};
    public static String[] suitList = {"Hearts", "Diamonds", "Clubs", "Spades"};
    public static int index;
    public static double balance;

    public static void main(String[] args) {
        playerList = FileHandling.readPlayerFile();
        System.out.println("\t\u001b[36m* Welcome to the Casino! *\u001b[0m");
        wait(2000);
        startUp();
    }

    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public static void startUp() {
        while (true) {
            System.out.println("\nWhat would you like to do?");
            System.out.println("1 - Login");
            System.out.println("2 - Register");
            System.out.println("0 - Exit");
            int userAnswer = input.nextInt();

            switch (userAnswer) {
                case 1:
                    index = getPlayerIndex();
                    if (index == -1) {
                        System.out.println("\u001b[31mSorry, this is not a valid player.\u001b[0m");
                    } else {
                        mainMenu();
                    }
                    break;
                case 2:
                    addPlayer();
                    break;
                case 0:
                    System.exit(0);
                    break;
            }
        }
    }

    public static void mainMenu() {
        while (true) {
            System.out.println("\nWhat would you like to do?");
            System.out.println("1 - Edit information");
            System.out.println("2 - Delete account");
            System.out.println("3 - View current balance");
            System.out.println("4 - Play Higher or Lower");
            System.out.println("5 - Play Slot Machines");
            System.out.println("6 - Play Roulette");
            System.out.println("7 - Play Black Jack");
            System.out.println("0 - Exit");
            int userAnswer = input.nextInt();

            switch (userAnswer) {
                case 1:
                    editPlayer();
                    break;
                case 2:
                    deletePlayer();
                    break;
                case 3:
                    viewBalance();
                    break;
                case 4:
                    HigherOrLower();
                    break;
                case 5:
                    slotMachine();
                    break;
                case 6:
                    roulette();
                    break;
                case 7:
                    blackJack();
                    break;
                case 0:
                    FileHandling.writePlayerFile(playerList);
                    System.exit(0);
                    break;
            }
        }
    }

    public static void addPlayer() {
        System.out.println("Please enter the Player's name");
        input.nextLine();
        String name = input.nextLine();
        System.out.println("Please enter the Player's username");
        String userName = input.nextLine();
        String password;
        while (true) {
            System.out.println("Please enter the Player's password. Please make this as strong as possible: must contain a number and must be at least 6 charcters long.");
            password = input.nextLine();
            if (password.length() > 6) {
                System.out.println("\u001b[31mSorry, this password isn't long enough!\u001b[0m");
            } else {
                break;
            }
        }

        System.out.println("Please enter the Player's email");
        String email = input.nextLine();

        Player newPlayer = new Player(name, userName, password, email, 100);
        playerList.add(newPlayer);
        System.out.println("Welcome " + name + ". You have a current balance of £100. Spend it wisely.");
    }

    public static int getPlayerIndex() {
        System.out.println("Please confirm your username");
        input.nextLine();
        String userName = input.nextLine();
        System.out.println("Please confirm your password");
        String password = input.nextLine();

        for (int i = 0; i < playerList.size(); i++) {
            if (playerList.get(i).getUserName().equals(userName) && playerList.get(i).getPassword().equals(password)) {
                return i;
            }
        }
        return -1;
    }

    public static void editPlayer() {
        int index = getPlayerIndex();

        if (index == -1) {
            System.out.println("\u001b[31mSorry, this is not a valid player.\u001b[0m");
        } else {
            System.out.println("What would you like to edit? (NOTE: you cannot edit your balance.)");
            System.out.println("1 - name");
            System.out.println("2 - username");
            System.out.println("3 - password");
            System.out.println("4 - email");
            System.out.println("5 - never mind");
            int userChoice = input.nextInt();

            switch (userChoice) {
                case 1:
                    System.out.println("Please type in the new name");
                    input.nextLine();
                    String newName = input.nextLine();
                    playerList.get(index).setName(newName);
                    break;
                case 2:
                    System.out.println("Please type in the new username");
                    input.nextLine();
                    String newUserName = input.nextLine();
                    playerList.get(index).setUserName(newUserName);
                    break;
                case 3:
                    System.out.println("Please type in the new password");
                    input.nextLine();
                    String newPassword = input.nextLine();
                    playerList.get(index).setPassword(newPassword);
                    break;
                case 4:
                    System.out.println("Please type in the new email");
                    input.nextLine();
                    String newEmail = input.nextLine();
                    playerList.get(index).setEmail(newEmail);
                    break;
                case 5:
                    break;
            }
            System.out.println("Player successfully changed to " + playerList.get(index).toString());
        }
    }

    public static void deletePlayer() {
        int index = getPlayerIndex();

        if (index == -1) {
            System.out.println("\u001b[31mSorry, this is not a valid player.\u001b[0m");
        } else if (playerList.get(index).getMoney() < 0) {
            System.out.println("Sorry, you cannot delete this player as they owe The Casino monney.");
        } else {
            System.out.println("Are you sure you want to delete user " + playerList.get(index).getUserName() + "? y or n");
            String userAnswer = input.next();
            if (userAnswer.equals("y")) {
                playerList.remove(index);
                System.out.println("Player successfully removed.");
            } else {
                System.out.println("Player not removed");
            }
        }
    }

    public static void viewBalance() {
        if (index == -1) {
            System.out.println("\u001b[31mSorry, that player doesn't exist!\u001b[0m");
        } else {
            double balance = playerList.get(index).getMoney();
            System.out.println("Your current balance is \u001b[36m£" + balance + "\u001b[0m");
        }
    }

    public static void takingMoney(double userBet) {
        balance = playerList.get(index).getMoney();
        balance = balance - userBet;
        playerList.get(index).setMoney(balance);
        System.out.println("Your current balance is £" + balance);
    }

    public static void HigherOrLower() {
        while (true) {
            System.out.println("\u001b[36mHIGHER OR LOWER - \u001b[0mTo win you must successfully guess whether the next card will be high or lower than the card already drawn 5 times in a row.");
            System.out.println("How much would you like to bet on higher or lower? If you win your bet will be tripled. Minimum entry is £5");
            System.out.println("0 - Exit");
            double userBet = input.nextInt();
            if (userBet == 0) {
                break;
            } else {
                if (playerList.get(index).getMoney() < userBet) {
                    System.out.println("\u001b[31mSorry, you don't have enough money to bet that!\u001b[0m");
                } else if (userBet < 5) {
                    System.out.println("\u001b[31mSorry, that isn't enough money to play on the slot machines!\u001b[0m");
                } else {
                    takingMoney(userBet);
                    System.out.print("Drawing first card");
                    wait(1000);
                    System.out.print(".");
                    wait(1000);
                    System.out.print(".");
                    wait(1000);
                    System.out.print(".");
                    int cardOne = rand.nextInt(13);
                    int suitOne = rand.nextInt(4);
                    int count = 5;
                    int rounds = 0;
                    while (count > 0) {
                        System.out.println("\nThe current card drawn is " + cardList[cardOne] + " of " + suitList[suitOne]);
                        System.out.println("Will the next card drawn be higher or lower? h or l");
                        String userAnswer = input.next();
                        int cardTwo = rand.nextInt(13);
                        int suitTwo = rand.nextInt(4);
                        System.out.println("The next card drawn is " + cardList[cardTwo] + " of " + suitList[suitTwo]);
                        if (userAnswer.equals("h")) {
                            if (cardOne > cardTwo) {
                                System.out.println("\u001b[31mSorry, you didn't win this time. Try again!\u001b[0m");
                                count = 0;
                            } else {
                                System.out.println("\u001b[32mYou guessed correctly!\u001b[0m");
                                count--;
                                rounds++;
                                System.out.println("You have currently won " + rounds + " rounds, you need " + count + " more to win.");
                                cardOne = cardTwo;
                                suitOne = suitTwo;
                            }
                        } else if (userAnswer.equals("l")) {
                            if (cardOne < cardTwo) {
                                System.out.println("\u001b[31mSorry, you didn't win this time. Try again!\u001b[0m");
                                count = 0;
                            } else {
                                System.out.println("\u001b[32mYou guessed correctly!\u001b[0m");
                                count--;
                                rounds++;
                                System.out.println("You have currently won " + rounds + " rounds, you need " + count + " more to win.");
                                cardOne = cardTwo;
                                suitOne = suitTwo;
                            }
                        }
                    }
                    if (rounds == 5) {
                        System.out.println("\nCongratulations! You have won Higher or Lower!");
                        calculateWinnings(index, 3, userBet, balance);
                        break;
                    } else {
                        break;
                    }
                }
            }
        }
    }

    public static void slotMachine() {
        String[] slotsList = {"Cherry", "Diamond", "Bell", "Coin", "Heart", "Star"};

        while (true) {
            System.out.println("\u001b[36mSLOT MACHINES - \u001b[0mTo win you must have three matching icons appear.");
            System.out.println("How much would you like to bet on the slot machine? If you win your bet will be doubled. Minimum entry is £3.");
            System.out.println("0 - Exit");
            double userBet = input.nextInt();
            if (userBet == 0) {
                break;
            } else {
                if (playerList.get(index).getMoney() < userBet) {
                    System.out.println("\u001b[31mSorry, you don't have enough money to bet that!\u001b[0m");
                } else if (userBet < 3) {
                    System.out.println("\u001b[31mSorry, that isn't enough money to play on the slot machines!\u001b[0m");
                } else {
                    takingMoney(userBet);
                    System.out.print("Rolling slot machine");
                    wait(1000);
                    System.out.print(".");
                    wait(1000);
                    System.out.print(".");
                    wait(1000);
                    System.out.print(".");
                    String slotOne = slotsList[(rand.nextInt(6))];
                    String slotTwo = slotsList[(rand.nextInt(6))];
                    String slotThree = slotsList[(rand.nextInt(6))];
                    System.out.println("\n" + slotOne + " | " + slotTwo + " | " + slotThree);
                    if (slotOne.equals(slotTwo) && slotOne.equals(slotThree)) {
                        System.out.println("\u001b[32mCongratulations! You have three matching slots!\u001b[0m");
                        calculateWinnings(index, 2, userBet, balance);
                    } else {
                        System.out.println("Sorry, you didn't win this time. Try again!");
                    }
                }
                break;
            }
        }
    }

    public static void roulette() {
        if (index == -1) {
            System.out.println("\u001b[31mSorry, that player doesn't exist!\u001b[0m");
        } else {
            System.out.println("\u001b[36mROULETTE -\u001b[0m Select to bet on either colour, odd or even, specific number and more. If the wheel lands on your bet, you win!");
            System.out.println("\nWhat would you like to bet on?");
            System.out.println("1 - Colour (whether the wheel lands on red or black. 50/50 chance)");
            System.out.println("2 - Odd or Even (whether the number landed on is odd or even)");
            System.out.println("3 - Specific Number (whether the wheel land on a specific number of your choosing. 1-36) ");
            System.out.println("4 - High or Low (whether the number landed on will be high (19-36) or low (1-18)");
            System.out.println("0 - Exit");
            int userAnswer = input.nextInt();
            switch (userAnswer) {
                case 1:
                    System.out.println("How much would you like to bet on \u001b[36mColour\u001b[0m? Your bet will be doubled. Minimum is £5");
                    System.out.println("0 - Exit");
                    double userBet = input.nextInt();
                    if (userBet == 0) {
                        break;
                    } else {
                        if (playerList.get(index).getMoney() < userBet) {
                            System.out.println("\u001b[31mSorry, you don't have enough money to bet that!\u001b[0m");
                        } else if (userBet < 5) {
                            System.out.println("\u001b[31mSorry, that isn't enough money to play Roulette!\u001b[0m");
                        } else {
                            takingMoney(userBet);
                            rouletteColour(userBet, index, balance);
                        }
                    }
                    break;
                case 2:
                    System.out.println("How much would you like to bet on \u001b[36mOdd or Even\u001b[0m? Your bet will be doubled. Minimum is £5");
                    System.out.println("0 - Exit");
                    double userMoney = input.nextInt();
                    if (userMoney == 0) {
                        break;
                    } else {
                        if (playerList.get(index).getMoney() < userMoney) {
                            System.out.println("\u001b[31mSorry, you don't have enough money to bet that!\u001b[0m");
                        } else if (userMoney < 5) {
                            System.out.println("\u001b[31mSorry, that isn't enough money to play Roulette!\u001b[0m");
                        } else {
                            takingMoney(userMoney);
                            rouletteOddEven(userMoney, index, balance);
                        }
                    }
                    break;
                case 3:
                    System.out.println("How much would you like to bet on \u001b[36mSpecific Number\u001b[0m? Your bet will be quadrupled (x4). Minimum is £1");
                    System.out.println("0 - Exit");
                    double userCash = input.nextInt();
                    if (userCash == 0) {
                        break;
                    } else {
                        if (playerList.get(index).getMoney() < userCash) {
                            System.out.println("\u001b[31mSorry, you don't have enough money to bet that!\u001b[0m");
                        } else if (userCash < 1) {
                            System.out.println("\u001b[31mSorry, that isn't enough money to play Roulette!\u001b[0m");
                        } else {
                            takingMoney(userCash);
                            rouletteSpecificNumber(userCash, index, balance);
                        }
                    }
                    break;
                case 4:
                    System.out.println("How much would you like to bet on \u001b[36mHigh or Low\u001b[0m? Your bet will be doubled. Minimum is £5");
                    System.out.println("0 - Exit");
                    double userDosh = input.nextInt();
                    if (userDosh == 0) {
                        break;
                    } else {
                        if (playerList.get(index).getMoney() < userDosh) {
                            System.out.println("\u001b[31mSorry, you don't have enough money to bet that!\u001b[0m");
                        } else if (userDosh < 5) {
                            System.out.println("\u001b[31mSorry, that isn't enough money to play Roulette!\u001b[0m");
                        } else {
                            takingMoney(userDosh);
                            rouletteHighLow(userDosh, index, balance);
                        }
                    }
                    break;
                case 0:
                    break;
            }
        }
    }

    public static void rouletteColour(double bet, int index, double balance) {
        while (true) {
            System.out.println("Which colour would you like to bet on? \u001b[31mRed(r)\u001b[0m or Black (b)?");
            String userAnswer = input.next();
            if (userAnswer.equals("r") || userAnswer.equals("b")) {
                System.out.print("Spinning wheel");
                wait(1000);
                System.out.print(".");
                wait(1000);
                System.out.print(".");
                wait(1000);
                System.out.print(".");
                int chosenColour = rand.nextInt(1);
                if (userAnswer.equals("r") && chosenColour == 0) {
                    System.out.println("\u001b[32mCongratulations! You have guessed correctly!\u001b[0m");
                    calculateWinnings(index, 2, bet, balance);
                } else if (userAnswer.equals("b") && chosenColour == 1) {
                    System.out.println("\u001b[32mCongratulations! You have guessed correctly!\u001b[0m");
                    calculateWinnings(index, 2, bet, balance);
                } else {
                    System.out.println("\u001b[31mSorry, you guessed incorrectly! Try again!\u001b[0m");
                }
                break;
            } else {
                System.out.println("\u001b[31mSorry, that option doesn't exist!\u001b[0m");
            }
        }
    }

    public static void rouletteOddEven(double bet, int index, double balance) {
        while (true) {
            System.out.println("What would you like to bet? Odd (o) or Even(e)?");
            String userAnswer = input.next();
            if (userAnswer.equals("o") || userAnswer.equals("e")) {
                System.out.print("Spinning wheel");
                wait(1000);
                System.out.print(".");
                wait(1000);
                System.out.print(".");
                wait(1000);
                System.out.print(".");
                int chosenNumber = rand.nextInt(35) + 1;
                int decider = chosenNumber % 2;
                if (decider == 0 && userAnswer.equals("e")) {
                    System.out.println("The number is " + chosenNumber + " which is even!");
                    System.out.println("\u001b[32mCongratulations! You have guessed correctly!\u001b[0m");
                    calculateWinnings(index, 2, bet, balance);
                } else if (decider == 1 && userAnswer.equals("o")) {
                    System.out.println("The number is " + chosenNumber + " which is odd!");
                    System.out.println("\u001b[32mCongratulations! You have guessed correctly!\u001b[0m");
                    calculateWinnings(index, 2, bet, balance);
                } else {
                    System.out.println("The number is " + chosenNumber + " which doesn't match your prediction.");
                    System.out.println("\u001b[31mSorry, you guessed incorrectly. Try again!\u001b[0m");
                }
                break;
            } else {
                System.out.println("\u001b[31mSorry, that option doesn't exist!\u001b[0m");
            }
        }
    }

    public static void rouletteSpecificNumber(double bet, int index, double balance) {
        while (true) {
            System.out.println("What number would you like to bet on? 1-36");
            int chosenNumber = input.nextInt();
            if (chosenNumber > 36 || chosenNumber < 0) {
                System.out.println("\u001b[31mSorry, that isn't a valid number!\u001b[0m");
            } else {
                System.out.print("Spinning Wheel");
                wait(1000);
                System.out.print(".");
                wait(1000);
                System.out.print(".");
                wait(1000);
                System.out.print(".");
                int generatedNumber = rand.nextInt(35) + 1;
                if (generatedNumber == chosenNumber) {
                    System.out.println("The number the wheel landed on was " + generatedNumber);
                    System.out.println("\u001b[32mCongratulations! You guessed correctly!\u001b[0m");
                    calculateWinnings(index, 4, bet, balance);
                } else {
                    System.out.println("The number the wheel landed on was " + generatedNumber);
                    System.out.println("\u001b[31mSorry, you guessed incorrectly! Try again!\u001b[0m");
                }
                break;
            }
        }
    }

    public static void rouletteHighLow(double bet, int index, double balance) {
        while (true) {
            System.out.println("What would you like to bet? High (1-18)(h) or Low (19-36)(l)");
            String userAnswer = input.next();
            if (userAnswer.equals("h") || userAnswer.equals("l")) {
                System.out.print("Spinning wheel");
                wait(1000);
                System.out.print(".");
                wait(1000);
                System.out.print(".");
                wait(1000);
                System.out.print(".");
                int generatedNumber = rand.nextInt(35) + 1;
                if (userAnswer.equals("h") && generatedNumber > 18) {
                    System.out.println("The wheel landed on " + generatedNumber);
                    System.out.println("\u001b[32mCongratulations! You guessed correctly!\u001b[0m");
                    calculateWinnings(index, 2, bet, balance);
                } else if (userAnswer.equals("l") && generatedNumber < 19) {
                    System.out.println("The wheel landed on " + generatedNumber);
                    System.out.println("\u001b[32mCongratulations! You guessed correctly!\u001b[0m");
                }
                break;
            } else {
                System.out.println("\u001b[31mSorry, that isn't a valid option.\u001b[0m");
            }
        }
    }

    public static void calculateWinnings(int index, int multiplier, double bet, double balance) {
        double winnings = bet * multiplier;
        playerList.get(index).setMoney(balance + winnings);
        balance = playerList.get(index).getMoney();
        System.out.println("£" + winnings + " has been added to your balance. Your current balance is £" + balance);
    }

    public static void blackJack() {
        while (true) {
            System.out.println("\u001b[36mBLACK JACK - \u001b[0m draw cards to try and get as close to 21 as possible. Winnings are inreased the closer you get to 21, but go over and you'll recieve nothing.");
            System.out.println("Minimum bet is £5");
            System.out.println("0 - Exit");
            double userBet = input.nextInt();

            if (userBet == 0) {
                break;
            } else {
                if (playerList.get(index).getMoney() < userBet) {
                    System.out.println("\u001b[31mSorry, you don't have enough money to bet that!\u001b[0m");
                } else if (userBet < 5) {
                    System.out.println("\u001b[31mSorry, that isn't enough money to play Black Jack!\u001b[0m");
                } else {
                    takingMoney(userBet);
                    System.out.print("Drawing first card");
                    wait(1000);
                    System.out.print(".");
                    wait(1000);
                    System.out.print(".");
                    wait(1000);
                    System.out.print(".");
                    int card = rand.nextInt(13);
                    int suit = rand.nextInt(4);
                    System.out.println("The first card drawn is " + cardList[card] + " of " + suitList[suit]);
                    int total = card + 1;
                    double winnings = userBet;
                    while (true) {
                        if (total > 21) {
                            System.out.println("\u001b[31mYour cards went over 21! You've lost. Try again!\u001b[0m");
                            break;
                        } else if (total == 21) {
                            System.out.println("\u001b[32mCongratulations! Your cards equal 21!\u001b[0m");
                            calculateWinnings(index, 5, winnings, balance);
                            break;
                        } else {
                            System.out.println("\nThe current total is " + total + ", you need " + (21 - total) + " to reach 21. Would you like to draw another card (1) or pull out (2)? Current winnings are £" + winnings);
                            int userAnswer = input.nextInt();
                            if (userAnswer == 1) {
                                int cardTwo = rand.nextInt(13);
                                int suitTwo = rand.nextInt(4);
                                System.out.println("The next card drawn is " + cardList[cardTwo] + " of " + suitList[suitTwo]);
                                total = total + (cardTwo + 1);
                                winnings = winnings * 1.5;
                            } else if (userAnswer == 2) {
                                playerList.get(index).setMoney(balance + winnings);
                                balance = playerList.get(index).getMoney();
                                System.out.println("\u001b[32m£" + winnings + " has been added to your balance. Your current balance is £" + balance + "\u001b[0m");
                                break;
                            }
                        }
                    }
                }
            }
            break;
        }
    }

}
