import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Toy {
    private int id;
    private String name;
    private int quantity;
    private double weight;

    public Toy(int id, String name, int quantity, double weight) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setQuantity(int i) {
    }
}

class ToyStore {
    private List<Toy> toys;

    public ToyStore() {
        toys = new ArrayList<>();
    }

    public void addToy(Toy toy) {
        toys.add(toy);
    }

    public void updateWeight(int toyId, double newWeight) {
        for (Toy toy : toys) {
            if (toy.getId() == toyId) {
                toy.setWeight(newWeight);
                break;
            }
        }
    }

    public void saveToysToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Toy toy : toys) {
                writer.println(toy.getId() + ";" + toy.getName() + ";" + toy.getQuantity() + ";" + toy.getWeight());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadToysFromFile(String filename) {
        toys.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                int quantity = Integer.parseInt(parts[2]);
                double weight = Double.parseDouble(parts[3]);
                Toy toy = new Toy(id, name, quantity, weight);
                toys.add(toy);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playLottery() {
        double totalWeight = 0;
        for (Toy toy : toys) {
            totalWeight += toy.getWeight();
        }

        Random random = new Random();
        double randomNumber = random.nextDouble() * totalWeight;
        double weightSum = 0;

        for (Toy toy : toys) {
            weightSum += toy.getWeight();
            if (randomNumber <= weightSum) {
                if (toy.getQuantity() > 0) {
                    toy.setQuantity(toy.getQuantity() - 1);
                    System.out.println("Congratulations! You won a " + toy.getName());
                } else {
                    System.out.println("Sorry, the " + toy.getName() + " is out of stock.");
                }
                break;
            }
        }
    }
}

public class Lotery {
    public static void main(String[] args) {
        ToyStore toyStore = new ToyStore();

        // Добавление игрушек
        toyStore.addToy(new Toy(1, "Teddy bear", 5, 30));
        toyStore.addToy(new Toy(2, "Doll", 3, 20));
        toyStore.addToy(new Toy(3, "Toy car", 7, 25));

        // Обновление веса игрушки
        toyStore.updateWeight(1, 40);

        // Сохранение игрушек в файл
        toyStore.saveToysToFile("toys.txt");

        // Загрузка игрушек из файла
        toyStore.loadToysFromFile("toys.txt");

        // Розыгрыш игрушек
        toyStore.playLottery();
    }

}


