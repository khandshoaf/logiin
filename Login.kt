import java.io.File
import kotlin.random.Random

data class User(val username: String, var password: String)
private val users = ArrayList<User>()

val src = "D:\\BaiTap\\InfoUser.txt"

fun readFromFile(){
    val file = File(src)
    if (file.exists()) {
        file.forEachLine {
            val userInfo = it.split(",")
            if(userInfo.size == 2){
                val (username, password) = userInfo
                users.add(User(username, password))
            } else {
                // Xử lý nếu định dạng đầu vào không chính xác
                println("Định dạng không chính xác trong tệp đầu vào: $it")
            }
        }
    }
}



fun saveToFile(){
    val file = File(src)
    file.bufferedWriter().use { out ->
        users.forEach {
            out.write("${it.username},${it.password}\n")
        }
    }
}

fun registerAccount(){
    while(true) {
        println("  ======AVATAR======  ")
        println("===ĐĂNG KÝ TÀI KHOẢN===")
        print("USER: ")
        var enterUsername: String? = readln()
        var usernameExists = false
        for (user in users) {
            if (user.username == enterUsername) {
                usernameExists = true
                break
            }
        }
        if (usernameExists) {
            println("Tài khoản đã tồn tại, vui lòng chọn tài khoản khác")
            print("User: ")
            enterUsername = readLine() ?: ""
        }
        print("Pass: ")
        var password: String? = readln()
        if (enterUsername == null) return
        if (password == null) return
        val newUser = User(enterUsername, password)

        if (newUser.username.length <= 6 || newUser.password.length <= 6) {
            println("Tài khoản hoặc mật khẩu không đuoc nhỏ hơn 6 ký tự")
            println("----------------------------------------------------")
        } else {
            users.add(newUser)
            saveToFile()
            println("Đăng ký thành công")
            println("----------------------------------------------------")
            break
        }
    }
}

fun loginAccount(){
    while(true) {
        println("=======ĐĂNG NHẬP VÀO GAME======")
        print("User: ")
        var enterUser: String? = readln()
        print("Pass: ")
        var enterPass: String? = readln()

        val foundUser = users.find { it.username == enterUser && it.password == enterPass }
        if (foundUser != null) {
            println("Đăng nhập thành công")
            println("------------------------------------------")
            break
        } else {
            println("Tài khoản hoặc mật khẩu không chính xác")
            println("-------------------------------------------")
        }
    }
}

fun forgotPass(){
    println("=====LẤY LẠI MẬT KHẨU=====")
    print("User: ")
    var enterUser:String? = readln()

    val otp = Random.nextInt(1000,9999)
    println("Mã OTP của bạn là: [$otp]")
    println()
    var usernameExists = false
    for (user in users) {
        if (user.username == enterUser) {
            print("Nhập mã otp: ")
            var enterOTP:Int? = readln()?.toInt()
            if(enterOTP == otp) {
                println("mật khẩu của bạn là:[ ${user.password} ]")
                println("--------------------------------------------")
                usernameExists = true
                break
            }else{
                println("Mã otp không đúng")
            }
        }
    }
    if (!usernameExists) {
        println("Tài khoản không đúng")
        println("--------------------------------------")
    }
}
fun changePass() {
    readFromFile()
    println("======ĐỔI MẬT KHẨU======")
    print("User: ")
    var enterUser: String = readLine() ?: ""
    print("Mật khẩu cũ : ")
    var enterPass: String = readLine() ?: ""
    print("Mật khẩu mới: ")
    var passNew: String = readLine() ?: ""

    val foundUser = users.find { it.username == enterUser && it.password == enterPass }
    if (foundUser != null) {
        if (enterPass == foundUser.password) {
            foundUser.password = passNew
            saveToFile() // Lưu mật khẩu mới vào file
            println(" Đổi mật khẩu thành công")
            println("Mật khẩu mới là: ${foundUser.password}")

            // Xóa mật khẩu cũ khỏi danh sách người dùng
            users.removeIf { it.username == enterUser && it.password == enterPass }

            // Lưu lại danh sách người dùng sau khi xóa mật khẩu cũ
            saveToFile()

            println("Đã xóa mật khẩu cũ khỏi file")
        }
    } else {
        println("Thông tin không chính xác")
    }
}

fun displayInfo(){
    readFromFile()
    println("=====Danh sách người dùng=====")
    for (user in users) {
        println("Username: ${user.username}, Password: ${user.password}")
        println("----------------------------------------------------")
    }
}
fun main(args: Array<String>) {
    do{
        println("1.Đăng ký tài khoản")
        println("2.Đăng nhập")
        println("3.Danh sách account đã join.")
        println("4.Lấy lại mật khẩu")
        println("5.Đổi mật khẩu")
        println("6.Thoát")
        println("===LỰA CHỌN CỦA BẠN===")
        var choose:Int? = readln()?.toInt()
        when(choose){
            1 -> registerAccount()
            2 -> loginAccount()
            3 -> displayInfo()
            4 -> forgotPass()
            5 -> changePass()
            6 -> {println("BẠN ĐÃ THOÁT GAME , see you again")
                break
            }
        }

    }while(choose != 6)
}