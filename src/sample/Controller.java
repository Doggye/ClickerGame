package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Controller {

    @FXML
    ImageView img11, img12, img13, img21, img22, img23;
    @FXML
    Button startButton, resumeButton, pauseButton, restartButton, restartButton2, restartRekord;
    @FXML
    Label lol;
    @FXML
    CheckBox mCheckBoxSounds, mCheckBoxMusic, mCheckBoxNiesmiertelnosc;
    @FXML
    MenuItem menuOpcje, menuExit;

    List<ImageView> mListaImageViews;
    Integer mPunkty = 0;
    StringProperty pkt, poziom, wynik;
    Timeline mTimelineGry;
    Integer mWylosowanePole = 0;
    Integer mObecnyPoziom = 1;
    Integer mEventowNaTimeline = 5;
    Integer mRekordowyWynik = 0;
    Integer mDlugoscEventu = 1000;
    Integer mMinCzasEventu = 210;
    Boolean mCzyGraDalej = true;
    Scene scena;
    List<KeyCode> klawisze;
    Scene scena1;
    Stage stage1;
    static Boolean dzwieki;
    static Boolean muzyka;
    static Boolean niesmiertelnosc;
    static double mVolume = 0.2;

    MediaPlayer mMediaPlayer = new MediaPlayer(new Media(getClass().getResource("/audio/lol.m4a").toExternalForm()));

    AudioClip mSoundClick = new AudioClip(getClass().getResource("/audio/click.mp3").toExternalForm());
    AudioClip mSoundError = new AudioClip(getClass().getResource("/audio/error.mp3").toExternalForm());
    AudioClip mSoundHappy = new AudioClip(getClass().getResource("/audio/happy.mp3").toExternalForm());

    Image mObrazOrange = new Image("/img/orangesq.png");
    Image mObrazYellow = new Image("/img/yellowsq.png");
    Image mObrazGreen = new Image("/img/green.png");


    public Controller() {

        pkt = new SimpleStringProperty();
        poziom = new SimpleStringProperty();
        wynik = new SimpleStringProperty();
        setPkt(String.format("Punkty: %d", mPunkty));
        setPoziom(String.format("Poziom: %d", mObecnyPoziom));


    }


    public String getWynik() {
        return wynik.get();
    }

    public StringProperty wynikProperty() {
        return wynik;
    }

    public void setWynik(String wynik) {
        this.wynik.set(wynik);
    }

    public String getPoziom() {
        return poziom.get();
    }

    public void setPoziom(String poziom) {
        this.poziom.set(poziom);
    }

    public StringProperty poziomProperty() {
        return poziom;
    }

    public String getPkt() {
        return pkt.get();
    }

    public void setPkt(String pkt) {
        this.pkt.set(pkt);
    }

    public StringProperty pktProperty() {
        return pkt;
    }

    public void setScena1(Scene scena1) {
        this.scena1 = scena1;
    }

    public void setStage1(Stage stage1) {
        this.stage1 = stage1;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // ZACHOWANIE APLIKACJI

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Inicjalizacja planszy


    public static void setDzwieki(Boolean dzwieki) {
        Controller.dzwieki = dzwieki;
    }

    public static Boolean getDzwieki() {
        return dzwieki;
    }

    public static Boolean getMuzyka() {
        return muzyka;
    }

    public static void setMuzyka(Boolean muzyka) {
        Controller.muzyka = muzyka;
    }

    public static Boolean getNiesmiertelnosc() {
        return niesmiertelnosc;
    }

    public static void setNiesmiertelnosc(Boolean niesmiertelnosc) {
        Controller.niesmiertelnosc = niesmiertelnosc;
    }

    public void start(ActionEvent event) {
        im();
        grajMuzyke();
        restartButton.setVisible(false);
        restartRekord.setVisible(false);
        for (ImageView iv : mListaImageViews) {
            iv.setOnMouseClicked(event1 -> koniec());
        }
        scena = img11.getScene();
        sprawdzOpcje();

    }

    private void im() {

        startButton.setVisible(false);
        pauseButton.setVisible(true);
        petlaGry();
        mTimelineGry.play();
        restart();

        mListaImageViews = new ArrayList<>();
        mListaImageViews.add(img21);
        mListaImageViews.add(img22);
        mListaImageViews.add(img23);
        mListaImageViews.add(img11);
        mListaImageViews.add(img12);
        mListaImageViews.add(img13);

        klawisze = new ArrayList<>(6);
        klawisze.add(KeyCode.NUMPAD1);
        klawisze.add(KeyCode.NUMPAD2);
        klawisze.add(KeyCode.NUMPAD3);
        klawisze.add(KeyCode.NUMPAD4);
        klawisze.add(KeyCode.NUMPAD5);
        klawisze.add(KeyCode.NUMPAD6);


        mListaImageViews.forEach(imageView -> imageView.setImage(mObrazOrange));


 /*       for (ImageView iv : mListaImageViews) {
            iv.setImage(mObrazOrange);
        }*/
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Start Timeline i jego zachowanie

    private void petlaGry() {

        nowyTimeline();
        mTimelineGry.getKeyFrames().add(new KeyFrame(Duration.millis(mDlugoscEventu), event1 -> {
            eventGry();
        }));
    }


    private void eventGry() {

        losowaniePola();
        setPoziom(String.format("Poziom: %d", mObecnyPoziom));
        for (ImageView pole : mListaImageViews) {
            if (mListaImageViews.get(mWylosowanePole).equals(pole)) {
                pole.setImage(mObrazGreen);
                pole.setOnMouseClicked(event -> {
                    mCzyGraDalej = true;
                    mPunkty += mObecnyPoziom;
                    setPkt(String.format("Punkty: %d", mPunkty));
                    pole.setImage(mObrazOrange);
                    sound(mSoundClick);
                    pole.setOnMouseClicked(event1 -> {
                        if (!niesmiertelnosc) {
                            koniec();
                        } else pole.setOnMouseClicked(null);

                    });
                });

            } else {
                pole.setImage(mObrazOrange);
                if (!niesmiertelnosc) {
                    pole.setOnMouseClicked(event -> koniec());
                } else pole.setOnMouseClicked(null);


            }

        }

        scena.setOnKeyPressed(event2 -> {

            if (event2.getCode().equals(klawisze.get(mWylosowanePole))) {
                sound(mSoundClick);
                mCzyGraDalej = true;
                mPunkty += mObecnyPoziom;
                setPkt(String.format("Punkty: %d", mPunkty));
                for (ImageView iv : mListaImageViews) {
                    iv.setImage(mObrazOrange);
                }
                if (klawisze.contains(event2.getCode())) {
                    if (!niesmiertelnosc) {
                        scena.setOnKeyPressed(event -> koniec());
                    }
                }

            } else if (klawisze.contains(event2.getCode())) {

                if (!niesmiertelnosc) {
                    koniec();
                    scena.setOnKeyPressed(event1 -> {
                        if (!niesmiertelnosc) {
                            koniec();
                            scena.setOnKeyPressed(null);
                        } else {
                            for (ImageView iv : mListaImageViews) {
                                iv.setOnMouseClicked(null);
                            }

                        }
                    });
                } else sound(mSoundClick);


            }

        });

        czyNiesmiertelny();

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Metody

    private void losowaniePola() {
        Random random = new Random();
        int poprzedniaLosowa = mWylosowanePole;
        do {
            mWylosowanePole = random.nextInt(5);
        } while (mWylosowanePole == poprzedniaLosowa);
    }

    private void czyNiesmiertelny() {
        if (!niesmiertelnosc) {
            if (mCzyGraDalej == false) {
                koniec();
            }
            mCzyGraDalej = false;
        }
    }

    private void sound(AudioClip sound) {
        if (dzwieki) {
            sound.play();
        }
    }

    private void infoZapis() {
        if (mPunkty > mRekordowyWynik) {
            mRekordowyWynik = mPunkty;
            sound(mSoundHappy);
            wynik.set(String.format("Najlepszy: %d%nNowy rekord!", mRekordowyWynik));
        } else {
            sound(mSoundError);
            wynik.set(String.format("Najlepszy: %d%nPrzegrałeś.", mRekordowyWynik));
        }
    }

    private void koniec() {

        stopButtony();
        stopTimeline();
        mMediaPlayer.stop();
        restartButton2.setVisible(true);
        startButton.setVisible(true);
        pauseButton.setVisible(false);
        resumeButton.setVisible(false);
        startButton.setVisible(false);
        restartButton.setVisible(false);
        startButton.setVisible(true);
        infoZapis();
        zapisPliku(mRekordowyWynik);


    }


    private void stopButtony() {
        try {
            for (ImageView iv : mListaImageViews) {
                iv.setOnMouseClicked(null);
            }
            scena.setOnKeyPressed(null);
        } catch (NullPointerException e) {
            System.out.println("");
        }

    }

    private void nowyTimeline() {
        stopTimeline();

        mTimelineGry = new Timeline();
        mTimelineGry.setCycleCount(mEventowNaTimeline);

        mTimelineGry.setOnFinished(event2 -> {
            mDlugoscEventu = (mDlugoscEventu * 95) / 100;
            if (mDlugoscEventu > mMinCzasEventu) {
                petlaGry();
                mTimelineGry.play();
                mObecnyPoziom++;
            } else {
                infoZapis();
                koniec();
            }
        });
    }

    private void stopTimeline() {
        if (mTimelineGry != null && mTimelineGry.getStatus() == Animation.Status.RUNNING) {
            mTimelineGry.stop();
        }
    }

    private void pauseTimeline() {
        if (mTimelineGry != null && mTimelineGry.getStatus() == Animation.Status.RUNNING) {
            mTimelineGry.pause();
        }
    }


    private void restart() {
        mPunkty = 0;
        mDlugoscEventu = 1000;
        mObecnyPoziom = 1;
        mWylosowanePole = 0;
        restartButton.setVisible(false);
        setPkt(String.format("Punkty: %d", mPunkty));
        setPoziom(String.format("Poziom: %d", mObecnyPoziom));
        wynik.set(String.format("Najlepszy: %d", mRekordowyWynik));
        restartRekord.setVisible(true);
    }


    public void Pause(ActionEvent event) {
        pauza();
    }


    private void pauza() {
        pauseTimeline();
        stopButtony();
        pauseButton.setVisible(false);
        resumeButton.setVisible(true);
        startButton.setVisible(false);
        mMediaPlayer.pause();
        restartButton.setVisible(true);
    }

    public void resume(ActionEvent event) {
        mTimelineGry.play();
        pauseButton.setVisible(true);
        resumeButton.setVisible(false);
        restartButton.setVisible(false);
        grajMuzyke();
    }

    private void grajMuzyke() {
        if (muzyka) {
            mMediaPlayer.play();
        }
    }

    public void restartGame(ActionEvent event) {
        im();
        for (ImageView iv : mListaImageViews) {
            iv.setImage(mObrazGreen);
        }
        stopTimeline();
        pauseButton.setVisible(false);
        resumeButton.setVisible(false);
        startButton.setVisible(true);
        restartButton2.setVisible(false);
        restartButton.setVisible(false);
        mMediaPlayer.stop();
        mCzyGraDalej = true;
    }


    private void zapisPliku(Integer rekordowyWynik) {

        try (
                FileOutputStream fos = new FileOutputStream("Resources/dane/rekord.ser");
                ObjectOutputStream oos = new ObjectOutputStream(fos);

        ) {
            oos.writeObject(rekordowyWynik);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int odczytPliku() {
        int liczba = 0;
        try (
                FileInputStream fis = new FileInputStream("Resources/dane/rekord.ser");
                ObjectInputStream ois = new ObjectInputStream(fis);


                BufferedInputStream bis = new BufferedInputStream(ois);
        ) {
            liczba = (int) ois.readObject();
            System.out.println(liczba);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return liczba;


    }


    public void restartRekord(ActionEvent event) {
        mRekordowyWynik = 0;
        zapisPliku(mRekordowyWynik);
        setWynik(String.format("Najlepszy: %d", mRekordowyWynik));
        testKlawiszy(event);

    }


    @FXML
    public void initialize() {
        mMediaPlayer.setVolume(mVolume);
        mRekordowyWynik = odczytPliku();
        setWynik(String.format("Najlepszy: %d", mRekordowyWynik));
        setMuzyka(true);
        setNiesmiertelnosc(false);
        setDzwieki(true);
        System.out.println("inicjalizacja gry");
        menuOpcje.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        menuExit.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));

    }

    public void testKlawiszy(Event event) {

    }

    public void opcjeMenu(ActionEvent event) throws IOException {
        sprawdzOpcje();

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/opcje.fxml"));
        stage1 = new Stage();
        scena = new Scene(root, 300, 250);
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.setTitle("Opcje");
        stage1.setScene(scena);
        stage1.show();
        stage1.setResizable(false);
        pauza();
    }

    private void sprawdzOpcje() {

        System.out.println("dzwieki: " + dzwieki);
        System.out.println("muzyka: " + muzyka);
        System.out.println("niesmiertelnosc: " + niesmiertelnosc);
    }


    public void opcjeExit(ActionEvent event) {

        Stage stage = (Stage) resumeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
}
