package rsa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;

/**
 *
 * @author Guilherme
 */
public class RSA {

        /*
        
        A princípio, para o funcionamento correto do projeto, é necessário um arquivo
        nomeado de "Mensagem.txt" na pasta padrão do projeto, dentro deste arquivo é inserido a mensagem
        que se deseja criptografar.
        Todas as chaves também serão armazenadas em arquivos txt na pasta padrão do projeto, com seus
        respectivos nomes.
        Exemplo: "p.txt" para o valor p.
        
        */

        
    public static void main(String[] args) throws IOException {
    
        /*
        
        Menu simples para a execução do projeto abaixo, com os respectivos valores:
        
        1 - Gerar as chaves através de números primos também gerados automaticamente.
        2 - Criptografar a mensagem no arquivo "Mensagem.txt".
        3 - Descriptografar a mensagem no arquivo "Mensagem Criptografada.txt" gerado após o processo de
        criptogração.
        4 - Executar o processo completo desde o início.
        0 - Finalizar.
        
        */
        
        int opcao = 1;
        Scanner sc = new Scanner(System.in);
        while(opcao != 0){
            System.out.println("Digite a opcão abaixo:\n1 - Gerar chaves\n2 - Criptografar a mensagem\n3 - Descriptografar a mensagem\n4 - Processo completo\n0- Finalizar");
            opcao = Integer.parseInt(sc.nextLine());
                if(opcao == 1){
                    gKeys(gPrimo(), gPrimo());
                }
                if (opcao == 2){
                    cripMsg();
                }
                if (opcao == 3){
                    decripMsg();
                }
                
                if (opcao == 4){
                    gKeys(gPrimo(), gPrimo());
                    cripMsg();
                    decripMsg();
                }
        }      
        
    }
        public static void gKeys(BigInteger p, BigInteger q) throws IOException{
            BigInteger m, n, d, e;
            
            /* Computa n = p * q */
            n = p.multiply(q);

            /* Computa a função totiente phi(n) = (p -1) (q -1) */
            m = (p.subtract(BigInteger.ONE))
                           .multiply(q.subtract(BigInteger.ONE));

            /* Escolhe um inteiro  "e"  , 1 < "e" < phi(n) ,  "e" e phi(n) sejam primos entre si. */
            e = new BigInteger("3");
            while(m.gcd(e).intValue() > 1) e = e.add(new BigInteger("2"));

            /* d seja inverso multiplicativo de "e". */
            d = e.modInverse(m);

            /* Informa os valores */
            System.out.println("p:"+p+"\nq:"+q+"\nn:"+n+"\ne:"+e+"\nd:"+d);            
            /* Salva os valores em seus respectivos arquivos .txt. */
            anotar("p",p);
            anotar("q",q);
            anotar("n",n);
            anotar("e",e);
            anotar("d",d);

            
        }
        public static BigInteger gPrimo(){
            /* Escolhe de forma aleatória um número primo grande, com bitlen sendo um auxilar de limite */
            int bitlen = 2048;            
            SecureRandom r = new SecureRandom();
            return new BigInteger(bitlen / 2, 100, r);
        }
        
        public static void cripMsg() throws IOException{
            /* Mensagem criptografada - RSA_encrypt()
               As funções "recuperarValorMsg" e "recuperarValor" leem os respectivos arquivos txt
               informados no parâmetro e retornam seu valor */
            String msg = recuperarValorMsg("Mensagem");
            String msgcrip = new BigInteger(msg.getBytes()).modPow(recuperarValor("e"), recuperarValor("n")).toString();
            System.out.println("Mensagem criptografada: "+ msgcrip);
            
            /* Chama a função para salvar a mensagem criptografada no arquivo "RSA - Mensagem criptografada.txt" */
            anotarMsg("RSA - Mensagem criptografada",msgcrip);
        }
        
        public static void decripMsg(){
            /* Mensagem descriptografada - RSA_decrypt() */
            String msgdescrip = new String(new BigInteger(recuperarValorMsg("RSA - Mensagem criptografada")).modPow(recuperarValor("d"), recuperarValor("n")).toByteArray());
            System.out.println("Mensagem descriptografada: " +msgdescrip); 
        }
        
        /* Função especializada em salvar o valor de um BigInteger em um txt com nome informado pelo
           parâmetro "filename" */
        public static void anotar(String filename, BigInteger n) throws IOException{
            String path = filename+".txt";
            BufferedWriter buffWrite = new BufferedWriter(new FileWriter(path));
            buffWrite.append(n.toString());
            buffWrite.close();
        }
        
        /* Função anterior, porém para salvar o valor de uma String em um txt */
        public static void anotarMsg(String filename, String msg) throws IOException{
            String path = filename+".txt";
            BufferedWriter buffWrite = new BufferedWriter(new FileWriter(path));
            buffWrite.append(msg);
            buffWrite.close();
        }        

        /* Função especializada em recuperar o valor de um BigInteger em um txt com nome informado pelo
           parâmetro "filename" */
        public static BigInteger recuperarValor(String filename){
            BigInteger bigIntegerStr = null;
            try {
                Scanner scanner = new Scanner(new File(filename+".txt"));
                String leitor=null;
                while (scanner.hasNext()) {
                    leitor = scanner.nextLine();				
                }
                bigIntegerStr=new BigInteger(leitor);
                scanner.close();
                return bigIntegerStr;
                }catch (IOException e) {
                    e.printStackTrace();
                }
            return bigIntegerStr;
        }

        /* Função anterior, porém para recuperar o valor de uma String em um txt */
        public static String recuperarValorMsg(String filename){
            String leitor=null;
            try {
                Scanner scanner = new Scanner(new File(filename+".txt"));    
                while (scanner.hasNext()) {
                    leitor = scanner.nextLine();				
                }
                scanner.close();
                return leitor;
                }catch (IOException e) {
                    e.printStackTrace();
                }
            return leitor;
        }
        
}