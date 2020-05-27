interface Saveable{
    void saveThisObject();
    Saveable retrieveThisObject(String name);

    // https://www.tutorialspoint.com/How-to-convert-Byte-Array-to-BLOB-in-java
    // https://www.sqlitetutorial.net/sqlite-java/jdbc-read-write-blob/
}