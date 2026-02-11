package com.br.passwordvault.console;

import com.br.passwordvault.entity.Credential;
import com.br.passwordvault.entity.User;
import com.br.passwordvault.service.CredentialService;
import com.br.passwordvault.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class TerminalRunner implements CommandLineRunner {

  private final UserService userService;
  private final CredentialService credentialService;
  private User currentUser = null;

  public TerminalRunner(UserService userService, CredentialService credentialService) {
    this.userService = userService;
    this.credentialService = credentialService;
  }

  @Override
  public void run (String... args) throws Exception {
    Scanner scanner = new Scanner(System.in);
    System.out.println("==================================");
    System.out.println(" WELCOME TO PASSWORD VAULT ");
    System.out.println("==================================");

    while (true) {
      if (currentUser == null) {
        showMainMenu(scanner);
      } else {
        showUserMenu(scanner);
      }
    }
  }

  private void showMainMenu(Scanner scanner) {
    System.out.println("\n--- MAIN MENU ---");
    System.out.println("1. Register User");
    System.out.println("2. Login");
    System.out.println("3. Exit");
    System.out.print("Option: ");

    String option = scanner.nextLine();

    switch (option) {
      case "1" -> register(scanner);
      case "2" -> login(scanner);
      case "3" -> {
        System.out.println("Bye bye!");
        System.exit(0);
      }
      default -> System.out.println("Invalid option");

    }

  }

  private void showUserMenu(Scanner scanner){
    System.out.println("\n--- VAULT MENU (User: " + currentUser.getUsername() + ") ---");
    System.out.println("1. Add New Password");
    System.out.println("2. View My Passwords");
    System.out.println("3. Logout");
    System.out.print("Option: ");

    String option = scanner.nextLine();

    switch (option) {
      case "1" -> addPassword(scanner);
      case "2" -> listPasswords();
      case "3" -> {
        System.out.println("Logging out...");
        currentUser = null;
      }
      default -> System.out.println("Invalid option!");
    }
  }

  private void register(Scanner scanner){
    System.out.print("Enter username: ");
    String username = scanner.nextLine();
    System.out.print("Enter master password: ");
    String password = scanner.nextLine();

    User newUser = new User(username, password);
    try{
      userService.registerUser(newUser);
      System.out.println("User registered successfully!");
    } catch (Exception e){
      System.out.println("Login failed: " + e.getMessage());
    }
  }

  private void login(Scanner scanner) {
    System.out.print("Enter username: ");
    String username = scanner.nextLine();
    System.out.print("Enter master password: ");
    String password = scanner.nextLine();

    try {
      currentUser = userService.login(username, password);
      System.out.println(" Login successful!");
    } catch (Exception e) {
      System.out.println(" Login failed: " + e.getMessage());
    }
  }

  private void addPassword(Scanner scanner){
    System.out.print("Service Name (ex: Netflix): ");
    String service = scanner.nextLine();
    System.out.print("Username/Email: ");
    String username = scanner.nextLine();
    System.out.print("Password: ");
    String password = scanner.nextLine();

    Credential credential = new Credential(service, username, password, currentUser);
    credentialService.addCredential(currentUser, credential);
    System.out.println("Password saved to your vault!");

  }

  public void listPasswords(){
    List<Credential> list = credentialService.getCredentials(currentUser);

    if (list.isEmpty()){
      System.out.println("Your vault is empty");
    }else{
      System.out.println("\n--- YOUR PASSWORDS ---");
      for (Credential c : list){
        System.out.println(c.toString());
      }
    }
  }
}

