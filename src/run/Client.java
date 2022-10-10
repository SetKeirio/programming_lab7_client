package run;

import exceptions.ConnectionFailedException;
import exceptions.ConnectionTroublesException;
import exceptions.LimitIgnoreException;
import messages.CommandMessage;
import messages.CommandResponse;
import messages.User;
import util.AuthorizationManager;
import util.Console;
import util.UserHandler;

import javax.smartcardio.CommandAPDU;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

//понять как отправляют через датаграммы и отправить
public class Client {
    private String host;
    private InetAddress hostAdress;
    private int port;
    private UserHandler handler;
    private DatagramPacket dp;
    private DatagramSocket ds;
    private DatagramChannel dc;
    private ByteBuffer buffer;
    private SocketAddress address;
    private User user;
    private AuthorizationManager authorizationManager;
    private final int standartLength = 16384;


    private int retryAttempt;
    private final int retryMaxAttempt, retryTimeout;

    public Client(String host, int port, int retryMaxAttempt, int retryTimeout, UserHandler handler, AuthorizationManager authorizationManager){
        this.host = host;
        this.port = port;
        this.retryMaxAttempt = retryMaxAttempt;
        this.retryTimeout = retryTimeout;
        this.handler = handler;
        this.authorizationManager = authorizationManager;
    }

    public void start(){
        boolean working = true;
        try{
            while (working){
                try{
                    connect();
                    working = sendRequest();
                }
                catch (ConnectionFailedException e){
                    if (retryAttempt > retryMaxAttempt){
                        Console.printerr("Не удалось подключиться " + retryMaxAttempt + " раз подряд.");
                        break;
                    }
                    try{
                        Thread.sleep(retryTimeout);
                    }
                    catch (IllegalArgumentException err){
                        Console.printerr("Ожидание подключения " + retryTimeout / 1000 + " секунд невозможно, переподключаюсь моментально.");
                    }
                    catch (InterruptedException fatal){
                        Console.printerr("Ошибка при ожидании подключения, переподключаюсь моментально.");

                    }
                }
                retryAttempt += 1;
            }
            Console.println("Клиент завершил свою работу.");
        }
        catch (LimitIgnoreException e){
            Console.printerr("Клиент не может быть запущен с такими параметрами.");
        }
        catch (Exception e){
            Console.printerr("Ошибка при разрыве соединения.");
            Console.printerr(e);
            e.printStackTrace();
        }
    }

    private void processAuthentication() {
        CommandMessage request = null;
        CommandResponse response = null;
        do {
            try {
                request = authorizationManager.authorize();
                if (request.isEmpty()) {
                    continue;
                }
                byte arr[];
                ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
                objectStream.writeObject(request);
                objectStream.flush();
                arr = byteStream.toByteArray();
                byteStream.flush();
                ds = new DatagramSocket();
                ds.setSoTimeout(retryTimeout);
                dp = new DatagramPacket(arr, arr.length, hostAdress, port);
                ds.send(dp);
                byteStream.close();
                objectStream.close();
                Console.println("Данные переданы на " + hostAdress + ":" + port);
                arr = new byte[standartLength];
                address = new InetSocketAddress(host, port + 1);
                dc = DatagramChannel.open();
                //dc.configureBlocking(false);
                buffer = ByteBuffer.wrap(arr);
                try {
                    address = dc.receive(buffer);
                    ByteArrayInputStream byteInput = new ByteArrayInputStream(arr);
                    ObjectInputStream objectInput = new ObjectInputStream(byteInput);
                    CommandResponse obj = (CommandResponse objectInput.readObject();
                    Console.print(obj.getResponceCode() + ": " + obj.getResponseBody());
                    Console.println("Данные получены от " + hostAdress + ":" + port);
                } catch (StreamCorruptedException e) {
                    Console.printerr("Сервер сейчас недоступен!");
                }
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (InvalidClassException | NotSerializableException exception) {
                Outputer.printerror("Произошла ошибка при отправке данных на сервер!");
            } catch (ClassNotFoundException exception) {
                Outputer.printerror("Произошла ошибка при чтении полученных данных!");
            } catch (IOException exception) {
                Outputer.printerror("Соединение с сервером разорвано!");
                try {
                    connectToServer();
                } catch (ConnectionErrorException | NotInDeclaredLimitsException reconnectionException) {
                    Outputer.println("Попробуйте повторить авторизацию позднее.");
                }
            }
        } while (serverResponse == null || !serverResponse.getResponseCode().equals(ResponseCode.OK));
        user = requestToServer.getUser();
    }

    public void connect() throws ConnectionTroublesException {
        try{
            hostAdress = InetAddress.getByName(host);
            Console.printerr("Хост найден.");
        }
        catch (UnknownHostException e){
            Console.printerr("Такого хоста не существует!");
            throw new ConnectionTroublesException();
        }
    }

    public boolean sendRequest(){
        CommandMessage request = new CommandMessage("", "");
        CommandResponse response = null;
        while (!request.getCommandName().equals("exit")){
            try{
                if (response != null){
                    request = handler.handle(response.getResponceCode());
                }
                else{
                    request = handler.handle(null);
                }
                if (request.isEmpty()){
                    continue;
                }
                else{
                    byte arr[];
                    ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                    ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
                    objectStream.writeObject(request);
                    objectStream.flush();
                    arr = byteStream.toByteArray();
                    byteStream.flush();
                    ds = new DatagramSocket();
                    ds.setSoTimeout(retryTimeout);
                    dp = new DatagramPacket(arr, arr.length, hostAdress, port);
                    ds.send(dp);
                    byteStream.close();
                    objectStream.close();
                    //ds = null;
                    //dp = null;
                    Console.println("Данные переданы на " + hostAdress + ":" + port);
                    arr = new byte[standartLength];
                    address = new InetSocketAddress(host, port + 1);
                    dc = DatagramChannel.open();
                    //dc.configureBlocking(false);
                    buffer = ByteBuffer.wrap(arr);
                    boolean goNext = true;
                    try {
                        address = dc.receive(buffer);
                        ByteArrayInputStream byteInput = new ByteArrayInputStream(arr);
                        ObjectInputStream objectInput = new ObjectInputStream(byteInput);
                        CommandResponse obj = (CommandResponse) objectInput.readObject();
                        Console.print(obj.getResponceCode() + ": " + obj.getResponseBody());
                        Console.println("Данные получены от " + hostAdress + ":" + port);
                    }
                    catch (StreamCorruptedException e){
                        Console.printerr("Сервер сейчас недоступен!");
                    }
                }
            }
            catch (IOException e){
                Console.printerr("Ошибка при обменом с на сервером!");
                e.printStackTrace();
                try{
                    retryAttempt += 1;
                    connect();
                }
                catch (ConnectionTroublesException ee){
                    Console.printerr("Повторите попытку отправки позже.");
                }

            }
            catch (ClassNotFoundException e){
                Console.printerr("Ошибка при получении ответа с сервера (класс не найден)! Попробуйте снова.");
                try{
                    retryAttempt += 1;
                    connect();
                }
                catch (ConnectionTroublesException ee){
                    Console.printerr("Повторите попытку отправки позже.");
                }
            }
            catch (NullPointerException e){
                Console.printerr("Ошибка при общении клиента и сервера! Попробуйте снова.");
                e.printStackTrace();
                try{
                    retryAttempt += 1;
                    connect();
                }
                catch (ConnectionTroublesException ee){
                    Console.printerr("Повторите попытку отправки позже.");
                }
            }

        }
        return true;
    }

}
