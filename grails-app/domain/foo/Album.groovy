package foo

class Album {

    String name
    Set songs

    static hasMany = [songs:Song]
    static constraints = {
    }
}
