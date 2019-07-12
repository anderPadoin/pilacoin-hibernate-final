package br.ufsm.csi.seguranca.pila.model;

import br.ufsm.csi.seguranca.pila.dao.HibernateDAO;
import br.ufsm.csi.seguranca.util.RSAUtil;
import br.ufsm.csi.seguranca.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Date;
import java.util.Random;

@Repository
public class Minerador implements Runnable {

    @Autowired
    private HibernateDAO hibernateDAO;

    private final BigInteger NUMERO = new BigInteger("99999998000000000000000000000000000000000000000000000000000000000000000");
    private Thread thread;
    private boolean status = false;

    public Minerador() {
    }

    public void iniciaMineracao() {
        System.out.println("iniciando mineração");
            this.thread = new Thread(this);
        this.status = true;
        this.thread.start();

    }
    public void terminaMineracao() {
        System.out.println("terminando mineração");
        this.status = false;
    }

    @Override
    public void run() {
        try {
            while (status) {
                Random random = new Random();
                PilaCoin pilaCoin = new PilaCoin();
                pilaCoin.setIdCriador("id-anderson");
                pilaCoin.setChaveCriador(RSAUtil.getPublicKey("chave_publica"));
                pilaCoin.setDataCriacao(new Date());

                Long magico;


                magico = random.nextLong();
                pilaCoin.setNumeroMagico(magico);

                byte[] pilaSerializado = Utils.serializa(pilaCoin);
                byte[] hashPilaSerializado = Utils.hashObject(pilaSerializado, "SHA-256");
                BigInteger numeroHash = new BigInteger(1, hashPilaSerializado);

                while (numeroHash.compareTo(NUMERO) >= 0 && status) {
                    pilaCoin.setNumeroMagico(random.nextLong());
                    hashPilaSerializado = Utils.hashObject(Utils.serializa(pilaCoin), "SHA-256");
                    numeroHash = new BigInteger(1, hashPilaSerializado);
                }
                if (!status) {
                    break;
                }
                System.out.println("Pila minerado");
                validaPila(pilaCoin);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void validaPila(PilaCoin pilaCoin) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);

        // Gera a chave de sessão
        SecretKey chaveDeSessao = keyGenerator.generateKey();

        // Abre uma conexão no endereço e porta indicados pelo response do servidor
        Socket socket = new Socket(DadosServidor.getEndereco(), DadosServidor.getPorta());

        // Criptografia da chave de sessão com a chave pública do servidor
        byte[] chaveCriptografada = Utils.criptografar("RSA", RSAUtil.getMasterPublicKey(), chaveDeSessao.getEncoded());

        // Serialização do pilas
        byte[] pilaSerializado = Utils.serializa(pilaCoin);

        // Assinatura do pilas serializado
        byte[] hashPilaSerializado = Utils.hashObject(pilaSerializado, "SHA-256");

        // Criptografia da assinatura com a chave privada do cliente.
        byte[] criptoHashSerializado = Utils.criptografar("RSA", RSAUtil.getPrivateKey("chave_privada"), hashPilaSerializado);

        // Criptografa o pilas serializado com a chave de sessão
        byte[] pilaSerializadoCripto = Utils.criptografar("AES", chaveDeSessao, pilaSerializado);

        // Instância do objeto troca e atribuição de campos
        ObjetoTroca objetoTroca = new ObjetoTroca();
        objetoTroca.setAssinatura(criptoHashSerializado);
        objetoTroca.setChaveSessao(chaveCriptografada);
        //objetoTroca.setChavePublica(RSAUtil.getPublicKey("chave_publica"));
        objetoTroca.setObjetoSerializadoCriptografado(pilaSerializadoCripto);

        // Envio do pilas para validação no servidor
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(objetoTroca);

        // Recebimento do pilas validado
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        Object objeto = in.readObject();
        in.close();
        out.close();
        socket.close();


        if (objeto instanceof ObjetoTroca) {

            ObjetoTroca objetoResponse = (ObjetoTroca) objeto;

            byte[] pilaRetornoCripto = objetoResponse.getObjetoSerializadoCriptografado();

            // Decriptografia do pilas recebido
            byte[] pilaRetornoSerial = Utils.decriptografar("AES", chaveDeSessao, pilaRetornoCripto);

            // Deserialização do pilas decriptografado
            PilaCoin pilaCoinValido = (PilaCoin) Utils.deserializa(pilaRetornoSerial);

            System.out.println("Pila valido! Salvo na base!");
            Log log = new Log();
            log.setDataHora(new Date());
            log.setUser(new User());
            log.setIdObjeto(pilaCoinValido.getId());
            log.setClasse(this.getClass());
            log.setTipo("mineracao");

            //hibernateDAO.criaObjeto(log);

            // Salva o pilas em disco
            /*File filePila = new File("src/pilas/pila_" + pilaCoinValido.getId());
            FileOutputStream fileOutputStream = new FileOutputStream(filePila);
            fileOutputStream.write(pilaRetornoCripto);*/
        } else {
            Mensagem mensagem = (Mensagem) objeto;
            System.out.println(mensagem.getErro());
        }
    }
}
