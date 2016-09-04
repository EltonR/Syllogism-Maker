package teste;

import factory.ConexaoFactory;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Teste {

    public static void main(String[] args) throws IOException, SQLException {

        BufferedReader br = null;
        String linha;
        int n = 0;
        ArrayList<String> lista = new ArrayList<String>();
        try {

            br = new BufferedReader(new FileReader("wiki.xml"));
            Connection connection = ConexaoFactory.conectar();
            while ((linha = br.readLine()) != null) {
                if (linha.startsWith("===") && linha.endsWith("===")) {
                    if (linha.startsWith("====Adjective====")) {
                        do {
                            System.out.println(linha);
                            linha = br.readLine();
                        } while (!linha.startsWith("==="));
                    } else if (linha.startsWith("====Noun====")) {
                        do {
                            System.out.println(linha);
                            linha = br.readLine();
                        } while (!linha.startsWith("==="));
                    } else if (linha.startsWith("===Verb===")) {
                        do {
                            System.out.println(linha);
                            linha = br.readLine();
                        } while (!linha.startsWith("==="));
                    } else if (linha.startsWith("===Adverb===")) {
                        do {
                            System.out.println(linha);
                            linha = br.readLine();
                        } while (!linha.startsWith("==="));
                    }else if (linha.startsWith("===Pronoun===")) {
                        do {
                            System.out.println(linha);
                            linha = br.readLine();
                        } while (!linha.startsWith("==="));
                    }else if (linha.startsWith("===Preposition===")) {
                        do {
                            System.out.println(linha);
                            linha = br.readLine();
                        } while (!linha.startsWith("==="));
                    }else if (linha.startsWith("===Conjunction===")) {
                        do {
                            System.out.println(linha);
                            linha = br.readLine();
                        } while (!linha.startsWith("==="));
                    }else if (linha.startsWith("===Determiner===")) {
                        do {
                            System.out.println(linha);
                            linha = br.readLine();
                        } while (!linha.startsWith("==="));
                    }else if (linha.startsWith("===Exclamation===")) {
                        do {
                            System.out.println(linha);
                            linha = br.readLine();
                        } while (!linha.startsWith("==="));
                    }else if(!lista.contains(linha)) {
                        lista.add(linha);
                    }
                }

                
                if (n % 10500 == 0) {
                    break;
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Teste.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(Teste.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (int i = 0; i < lista.size(); i++) {
            System.out.println(lista.get(i));
        }
    }

    public static void main2(String[] args) throws IOException, SQLException {

        BufferedReader br = null;
        String linha;
        int n = 0;

        try {

            br = new BufferedReader(new FileReader("dicionario.txt"));
            Connection connection = ConexaoFactory.conectar();
            while ((linha = br.readLine()) != null) {
                if (linha.length() > 2) {
                    if (!linha.startsWith("Usage")) {
                        String first_word = linha.substring(0, linha.indexOf(" "));
                        linha = linha.substring(linha.indexOf(" ")).trim();
                        if (!first_word.endsWith("-") && !first_word.startsWith("-")) {
                            while (!first_word.endsWith(".")) {
                                if (linha.indexOf(" ") == -1) {
                                    first_word = "";
                                    break;
                                }
                                first_word += " " + linha.substring(0, linha.indexOf(" "));
                                linha = linha.substring(linha.indexOf(" ")).trim();
                            }
                            if (!first_word.equalsIgnoreCase("")) {
                                String tipo = first_word.substring(first_word.lastIndexOf(" ", 25)).trim();
                                first_word = first_word.substring(0, first_word.lastIndexOf(" ", 25)).trim();
                                PreparedStatement sql = connection.prepareStatement("INSERT INTO dicionario(termo, tipo, definicao) VALUES (?,?,?)");
                                sql.setString(1, first_word);
                                sql.setString(2, tipo);
                                sql.setString(3, linha);
                                sql.executeUpdate();
                            }
                        }

                    }
                }
                n++;
                if (n % 500 == 0) {
                    System.out.println(n);
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Teste.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(Teste.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
