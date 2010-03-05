package foo

class Song {

    String name
    Album album

    static belongsTo = [album: Album]
    static constraints = {
        album(nullable:true)
    }
}
