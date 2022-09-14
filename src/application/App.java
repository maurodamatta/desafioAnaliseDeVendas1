package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Sale;

public class App {
    public static void main(String[] args){
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter full file path: ");
        String path = sc.nextLine();
        
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            List<Sale> list = new ArrayList<>();

            String line = br.readLine();
            while (line != null) {
                String[] fields = line.split(",");
                list.add(new Sale(Integer.parseInt( fields[0]), Integer.parseInt(fields[1]), fields[2], Integer.parseInt(fields[3]), Double.parseDouble(fields[4])));
                line = br.readLine();
            }

            Comparator<Sale> comp = (s1, s2) -> s1.averagePrice().compareTo(s2.averagePrice()); 

            List<Sale> s = list.stream()
                .filter(x -> x.getYear() == 2016)
                .sorted(comp.reversed())
                .limit(5)
                .collect(Collectors.toList());
            s.forEach(System.out::println);

            double sum = list.stream()
                .filter(x -> x.getSeller().equals("Logan"))
                .filter(x -> x.getMonth() == 1 || x.getMonth() == 7)
                .map(x -> x.getTotal())
                .reduce(0.0, (x,y) -> x + y);
                System.out.println("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = " + String.format("%.2f", sum));

        } catch (IOException e) {
            System.out.println("Erro: " + path + "(O sistema não pode encontrar o arquivo especificado)");
        }
        sc.close();
    }
}
