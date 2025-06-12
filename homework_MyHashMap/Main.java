package homework_MyHashMap;

public class Main {
    public static void main(String[] args) {
        MyHashMap<Key, String> map = new MyHashMap<>();

        Key key1 = new Key("тип здания");
        Key key2 = new Key("адрес");

        map.put(key1, "Театр");
        map.put(key2, "ул. Пушкина, 25");

        System.out.println("Тип здания: " + map.get(key1));
        System.out.println("Есть адрес? " + map.containsKey(key2));

        map.put(key1, "Дом культуры");
        System.out.println("Новое значение для типа здания: " + map.get(key1));

        map.remove(key2);
        System.out.println("Есть адрес после удаления? " + map.containsKey(key2));
    }
}
