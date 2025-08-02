fun main() {
    WallService.add(Post(0, "Привет! это первый пост", 0, 0))
    WallService.add(Post(0, "Это второй пост", 0, 0))
    WallService.printPost()
    WallService.update(Post(2, "Пост обновлен: теперь он такой", 0, 0))
    WallService.printPost()

}

data class Post(val id: Int, val text: String, var views: Int, var likes: Int) {
}

object WallService {
    var posts = emptyArray<Post>()
    var lastId = 0

    fun add(post: Post): Post {
        posts += post.copy(++lastId)
        return posts.last()
    }

    fun update(new: Post): Boolean {
        for ((index, post) in posts.withIndex()) {
            if (new.id == post.id) {
                posts[index] = new.copy()
                return true
            }
        }
        return false
    }

    fun printPost() {
        for (post in posts) {
            println(post)
            println()
        }
    }
}

