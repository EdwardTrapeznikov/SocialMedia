import java.time.LocalDateTime
import kotlin.math.truncate

fun main() {
    WallService.add(Post(0,0,"Edward","Hello, its my first post",0,
    0,0,false, false, true, false,
    Comments(), Likes()))
    WallService.add(Post(0,0,"Edward","Hello, its my second post",0,
        0,0,false, false, true, false,
        Comments(), Likes()))
    WallService.printPost()

}

data class Post(
    val id: Int = 0,
    val authorId: Int = 0,
    val authorName: String = "",
    val text: String = "",
    val viewsCount: Int = 0,
    val likesCount: Int = 0,
    val sharesCount: Int = 0,
    val isLiked: Boolean = false,
    val isShared: Boolean = false,
    val isPublished: Boolean = true,
    val isRepost: Boolean = false,
    val comments: Comments = Comments(),
    val likes: Likes = Likes()
)

data class Comments(
    val count: Int = 0,
    val canComment: Boolean = true,
    val canSeeAll: Boolean = true,
    val hasMore: Boolean = false
)

data class Likes(
    val count: Int = 0,
    val canLike: Boolean = true,
    val canPublish: Boolean = true,
    val userLikes: Boolean = false,
    val myLike: Boolean = false
)

object WallService {
    var posts = emptyArray<Post>()
    var lastId = 0

    fun clear() {
        posts = emptyArray()
        lastId = 0
    }

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

