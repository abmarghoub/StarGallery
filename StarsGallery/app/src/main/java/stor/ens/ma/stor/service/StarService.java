package stor.ens.ma.stor.service;

import java.util.ArrayList;
import java.util.List;

import stor.ens.ma.stor.beans.Star;
import stor.ens.ma.stor.dao.IDao;

public class StarService implements IDao<Star> {
    private List<Star> stars;
    private static StarService instance;

    private StarService() {
        stars = new ArrayList<>();
        seed();
    }

    public static StarService getInstance() {
        if (instance == null) instance = new StarService();
        return instance;
    }

    private void seed() {

        stars.add(new Star("Cristiano Ronaldo", "https://th.bing.com/th/id/R.93c7dfb29c57ffeb4397a8e8293ba25a?rik=4y6uHvCx4U%2bA9A&riu=http%3a%2f%2fimages.performgroup.com%2fdi%2flibrary%2fGOAL%2ffb%2f61%2fcristiano-ronaldo-real-madrid_1pfdgxah3q4io1rplb5mlct7ud.jpg&ehk=thCYK0falkOlJl62r%2fDj3UX5i7SR4qSdd0jJ2tV6EAc%3d&risl=&pid=ImgRaw&r=0", 5.0f));
        stars.add(new Star("Alfredo Di Stéfano", "https://tse2.mm.bing.net/th/id/OIP.oWBbVe43oVcgp7nZKx74jwHaMd?rs=1&pid=ImgDetMain&o=7&rm=3", 5.0f));
        stars.add(new Star("Raúl González", "https://www.periodistadigital.com/wp-content/uploads/2015/10/raul-gonzalez.jpg", 4.9f));
        stars.add(new Star("Zinedine Zidane", "https://th.bing.com/th/id/R.12a4821f40dbdb64b2dbcfab3a3bb980?rik=HJshciCg1FbW4g&riu=http%3a%2f%2f1.bp.blogspot.com%2f-W0snJhdWX04%2fTkszdZglDHI%2fAAAAAAAAAXI%2ffMIEjicw8So%2fs1600%2fZidane%2b2.jpg&ehk=pjCYvMINvf6RXSAQbP5MRDCKscNhWzAF2%2f6dSZgVdsk%3d&risl=&pid=ImgRaw&r=0", 4.9f));
        stars.add(new Star("Sergio Ramos", "https://ichef.bbci.co.uk/onesport/cps/624/cpsprodpb/A1EF/production/_118955414_gettyimages-691957402.jpg", 4.8f));
        stars.add(new Star("Iker Casillas", "https://th.bing.com/th/id/R.2d208501b643a1d7de63757a120d05f1?rik=Ks%2bXmGZhdcU7Aw&pid=ImgRaw&r=0", 4.9f));
        stars.add(new Star("Luka Modrić", "https://th.bing.com/th/id/OIP.eGgzzBs8tRYA-MqSRUn4tgHaEK?w=295&h=180&c=7&r=0&o=7&dpr=1.3&pid=1.7&rm=3", 4.9f));
        stars.add(new Star("Roberto Carlos", "https://th.bing.com/th/id/R.0a8570a741ac05aa498508ae264ebe09?rik=eizED9eWO8QTIA&riu=http%3a%2f%2fimages.performgroup.com%2fdi%2flibrary%2fgoal_es%2fb6%2f1d%2froberto-carlos-real-madrid_1559gwpim5d3g1alm8vp9r4wif.jpg%3ft%3d1065516801%26&ehk=5g%2fyKHxGXmT2N%2br1MNyJrVPgfekFFKySmw4oJQrnIKo%3d&risl=1&pid=ImgRaw&r=0", 4.8f));
        stars.add(new Star("Karim Benzema", "https://e0.365dm.com/23/04/2048x1152/skysports-benzema-real-madrid_6108840.jpg?20230402215610", 4.9f));
        stars.add(new Star("Luis Figo", "https://th.bing.com/th/id/R.a8f1bbd712594a59e0757acebafe0c4e?rik=3%2fO264wchRcpAg&pid=ImgRaw&r=0", 4.8f));
        stars.add(new Star("Ronaldo Nazário", "https://i.pinimg.com/736x/1d/e9/7e/1de97e2d54085bbf3c248e79f1723c2e.jpg", 4.8f));
        stars.add(new Star("David Beckham", "https://media.gettyimages.com/id/2530516/photo/david-beckham-of-real-madrid-celebrates.jpg?s=1024x1024&w=gi&k=20&c=C7m_ddOeAiacLReqnR-pGB1igoSTpfPvWv9bxQQvlic=", 4.8f));
        stars.add(new Star("Xabi Alonso", "https://th.bing.com/th/id/R.ae673443f6763e45575de22c029919bf?rik=CUUJswe0btG9Aw&pid=ImgRaw&r=0", 4.8f));
        stars.add(new Star("Casemiro", "https://icdn.caughtoffside.com/wp-content/uploads/2022/08/real-madrid-casemiro-celebrations.jpg", 4.8f));
        stars.add(new Star("Toni Kross", "https://icdn.football-italia.net/wp-content/uploads/2024/02/real-madrid-cf-v-ssc-napoli-group-c-uefa-champions-league-2023-24-1024x683.jpg", 4.8f));


    }

    @Override
    public boolean create(Star o) {
        return stars.add(o);
    }

    @Override
    public boolean update(Star o) {
        for (Star s : stars) {
            if (s.getId() == o.getId()) {
                s.setName(o.getName());
                s.setImg(o.getImg());
                s.setRating(o.getRating());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(Star o) {
        return stars.remove(o);
    }

    @Override
    public Star findById(int id) {
        for (Star s : stars) {
            if (s.getId() == id) return s;
        }
        return null;
    }

    @Override
    public List<Star> findAll() {
        return stars;
    }
}
