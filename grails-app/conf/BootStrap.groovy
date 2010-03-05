import foo.*

class BootStrap {

     def init = { servletContext ->

        def song1 = new Song(name:'song1').save()
        def song2 = new Song(name:'song2').save()

        def album1 = new Album(name:'album1').save()
        def album2 = new Album(name:'album2').save()
        def album3 = new Album(name:'album3').save()

        album2.addToSongs(song1).save()
        album3.addToSongs(song2).save()


     }
     def destroy = {
     }
} 