package app.natateam.maptest.db;

import app.natateam.maptest.MapApp;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by macbook on 25/07/ 15.
 */
public class RealmHelper {
    private static RealmHelper realmHelper;
    private Realm realm;
    public static RealmHelper getInstanse(){
        if (realmHelper ==null){
            realmHelper = new RealmHelper();
        }
        return realmHelper;
    }

    public RealmHelper(){
        RealmConfiguration config = new RealmConfiguration.Builder(MapApp.getInstanse())
                .name("maprealm")
                .schemaVersion(0).inMemory()
                .build();
        realm = Realm.getInstance(config);
        setFlats();
    }

    public Realm getRealm() {
        return realm;
    }

    public void setFlats(){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (int i=0;i<5;i++){
                    Flat flat = realm.createObject(Flat.class);
                    flat.setId(i);
                    switch (i){
                        case 0:{
                            flat.setLat(55.7722938);
                            flat.setLon(37.6067233);
                            flat.setAdress("ул. Каретный Ряд, 5/10с2, кв. 12");
                            flat.setImage("http://happymodern.ru/wp-content/uploads/2015/03/Kvartira-studiya-16.jpg");
                            flat.setFloor("3/12");
                            flat.setSquare(55);
                            flat.setCost("1500 р /сутки");
                            flat.setRoom_count(2);
                            break;
                        }
                        case 1:{
                            flat.setLat(55.773029);
                            flat.setLon(37.6252223);
                            flat.setAdress("Садовая-Сухаревская ул., 14, кв. 12");
                            flat.setImage("http://www.idesignarch.com/wp-content/uploads/Saint-Petersburg-Studio-Apartment_1.jpg");
                            flat.setFloor("3/12");
                            flat.setSquare(55);
                            flat.setCost("1500 р /сутки");
                            flat.setRoom_count(2);
                            break;
                        }
                        case 2:{
                            flat.setLat(55.7534303);
                            flat.setLon(37.651565);
                            flat.setAdress("ул. Воронцово Поле, 16c5, кв. 12");
                            flat.setImage("http://stoydiz.ru/wp-content/gallery/dizajn-odnokomnatnoj-kvartiry-2/apartment_litvinov_1.jpg");
                            flat.setFloor("3/12");
                            flat.setSquare(55);
                            flat.setCost("1500 р /сутки");
                            flat.setRoom_count(2);
                            break;
                        }
                        case 3:{
                            flat.setLat(55.7327556);
                            flat.setLon(37.6162083);
                            flat.setAdress("1-й Спасоналивковский пер., 3, кв. 12");
                            flat.setImage("http://premium-sk.ru/files/img_2874-1.jpg");
                            flat.setFloor("3/12");
                            flat.setSquare(55);
                            flat.setCost("1500 р /сутки");
                            flat.setRoom_count(2);
                            break;
                        }
                        case 4:{
                            flat.setLat(55.7492714);
                            flat.setLon(37.5822183);
                            flat.setAdress("Новинский б-р, 3, строен. 1, кв. 12");
                            flat.setImage("https://formo.ua/files/img/Image/4/45/4fd1dd8ed4753.jpg");
                            flat.setFloor("3/12");
                            flat.setSquare(55);
                            flat.setCost("1500 р /сутки");
                            flat.setRoom_count(2);
                            break;
                        }
                    }
                }
            }
        });
    }
}
