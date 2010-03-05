package foo

class AlbumController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [albumInstanceList: Album.list(params), albumInstanceTotal: Album.count()]
    }

    def create = {
        def albumInstance = new Album()
        albumInstance.properties = params
        return [albumInstance: albumInstance]
    }

    def save = {
        def albumInstance = new Album(params)
        if (albumInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'album.label', default: 'Album'), albumInstance.id])}"
            redirect(action: "show", id: albumInstance.id)
        }
        else {
            render(view: "create", model: [albumInstance: albumInstance])
        }
    }

    def show = {
        def albumInstance = Album.get(params.id)
        if (!albumInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'album.label', default: 'Album'), params.id])}"
            redirect(action: "list")
        }
        else {
            [albumInstance: albumInstance]
        }
    }

    def edit = {
        def albumInstance = Album.get(params.id)
        if (!albumInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'album.label', default: 'Album'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [albumInstance: albumInstance]
        }
    }

    def update = {

        println "---------------"
        println "Inital Params map ------->" + params
        println "---------------"
        params.put "songs", "1"
        println "---------------"
        println "Song 1 added to the params ------->" + params
        println "---------------"

        def albumInstance = Album.get(params.id)
        if (albumInstance) {

            println "----------------------------------"
            println "Album data before 1st binding -------"
            println albumInstance.dump()
            println "----------------------------------"

            albumInstance.properties = params

            println "----------------------------------"
            println "Album data after 1st binding -------"
            println albumInstance.dump()
            println "----------------------------------"

            params."songs" = "2"

            println "----------------------------------"
            println "Changed song 1 to song 2 in the params Map ------->" + params
            println "----------------------------------"

            albumInstance.properties = params

            println "----------------------------------"
            println "Album after 2nd binding -------"
            println albumInstance.dump()
            println "----------------------------------"

            if (!albumInstance.hasErrors() && albumInstance.save(flush: true)) {

                println "----------------------------------"
                println "Album after save -------"
                println albumInstance.dump()
                println "----------------------------------"

                println "----------------------------------"
                println "Songs data after save -------"
                println Song.get(1).dump()
                println Song.get(2).dump()
                println "----------------------------------"

                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'album.label', default: 'Album'), albumInstance.id])}"
                redirect(action: "show", id: albumInstance.id)
            }
            else {
                render(view: "edit", model: [albumInstance: albumInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'album.label', default: 'Album'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def albumInstance = Album.get(params.id)
        if (albumInstance) {
            try {
                albumInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'album.label', default: 'Album'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'album.label', default: 'Album'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'album.label', default: 'Album'), params.id])}"
            redirect(action: "list")
        }
    }
}
