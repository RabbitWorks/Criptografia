package aes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Scanner;

/**
 *
 * @author Guilherme
 */
public class AES {
    
        /*
        
        A princípio, para o funcionamento correto, é necessário um arquivo
        nomeado de "Mensagem.txt" na pasta padrão do projeto, dentro deste arquivo é inserido a mensagem
        que se deseja criptografar.
        A chave também deve ser informada em um arquivo de texto, chamado de "AES - Chave.txt" onde
        o tamanho da chave deve ser entre:
        32 caracteres -> 256 bits
        24 caracteres -> 192 bits
        16 caracteres -> 128 bits
        */
    
    public static void main(String[] args) throws IOException {
        String chave = recuperarValorMsg("AES - Chave");
        System.out.println("Chave: "+chave);
        SecretKey key = new SecretKeySpec(chave.getBytes(), "AES"); 
        
        
        try {
            Cipher cipher = Cipher.getInstance("AES");

            cipher.init(Cipher.ENCRYPT_MODE, key);	 	 
            /* Recupera a mensagem a ser criptografada no arquivo "Mensagem.txt" */
            String msg = recuperarValorMsg("Mensagem");	 	 
            /* Criptografa a mensagem */	 	 
            byte[] msgcrip = cipher.doFinal(msg.getBytes());
            /* Salva a mensagem criptografada em seu respectivo txt */
            anotarMsg("AES - Mensagem criptografada",msgcrip.toString());
            
            /* Exibe a mensagem criptografada salva */	 	 
            System.out.println("Mensagem criptografada: "+ recuperarValorMsg("AES - Mensagem criptografada"));
            /* Informa ação de descriptografar */	 	 
            cipher.init(Cipher.DECRYPT_MODE, key);	 	 
            /* Recebe a mensagem criptografada e descriptografa */
            byte[] msgdescrip = cipher.doFinal(msgcrip);	 	 
            /* Converte para a base 64 e amazena a mensagem em uma variavel */
            String msgOriginal = new String(msgdescrip);	 	 

            /* Exibe a mensagem descriptografada */
            System.out.println("Mensagem descriptografada: " + msgOriginal);
        }catch (Exception e) {
            e.printStackTrace();
        }
        
        
        
    }


    
    //Função especializada em armazenar o valor em um txt
    public static void anotarMsg(String filename, String msg) throws IOException{
        String path = filename+".txt";
        BufferedWriter buffWrite = new BufferedWriter(new FileWriter(path));
        buffWrite.append(msg);
        buffWrite.close();
    } 
    
    //Função especializada em recuperar o valor de uma String em um txt
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
