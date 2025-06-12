package homework_MyHashMap;


public class Key {

    final String key;

    public Key(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public boolean myEquals(Object obj) {
        boolean isEquals = false;

        if (this == obj) {
            isEquals = true;
        }
        else if (obj instanceof String) {
            String other = (String) obj;

            if (this.key.length() == other.length()) {
                isEquals = true;

                for (int i = 0; i < this.key.length() && isEquals; i++) {
                    if (this.key.charAt(i) != other.charAt(i)) {
                        isEquals = false;
                    }
                }
            }
        }

        return isEquals;
    }

    public int myHashCode() {
        int hash = 0;
        if (key != null) {
            for (int i = 0; i < key.length(); i++) {
                hash = 31 * hash + key.charAt(i);
            }
        }
        return hash;
    }
}
