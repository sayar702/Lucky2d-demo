package com.example.lucky2d

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.widget.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : Activity() {
    private val red = Color.rgb(152, 21, 29)
    private val ink = Color.rgb(45, 35, 35)
    private val records = mutableListOf<String>()
    private var selectedNumber: String? = null
    private var language = "my"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showLogin()
    }

    private fun label(my: String, th: String) = if (language == "my") my else th

    private fun baseLayout() = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
        setPadding(18, 18, 18, 18)
        setBackgroundColor(Color.rgb(255, 248, 244))
    }

    private fun heading(parent: LinearLayout, text: String) {
        parent.addView(TextView(this).apply {
            this.text = text
            textSize = 25f
            setTextColor(red)
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER
            setPadding(0, 12, 0, 18)
        })
    }

    private fun button(parent: LinearLayout, text: String, action: () -> Unit) {
        parent.addView(Button(this).apply {
            this.text = text
            setTextColor(Color.WHITE)
            setBackgroundColor(red)
            setOnClickListener { action() }
        })
    }

    private fun field(hintText: String, password: Boolean = false) =
        EditText(this).apply {
            hint = hintText
            textSize = 16f
            if (password) inputType = 129
        }

    private fun showLogin() {
        val root = baseLayout()
        heading(root, "🍀 LUCKY 2D")
        root.addView(TextView(this).apply {
            text = "DEMO ONLY • ငွေလောင်းကြေး/ငွေပေးချေမှု မပါဝင်ပါ"
            setTextColor(red); gravity = Gravity.CENTER; textSize = 12f
        })
        root.addView(Button(this).apply {
            text = "မြန်မာ / ไทย"
            setOnClickListener { language = if (language == "my") "th" else "my"; showLogin() }
        })
        val phone = field(label("ဖုန်းနံပါတ်", "เบอร์โทรศัพท์"))
        val pass = field(label("စကားဝှက်", "รหัสผ่าน"), true)
        root.addView(phone); root.addView(pass)
        button(root, label("User ဝင်မည်", "เข้าสู่ระบบผู้ใช้")) {
            if (phone.text.isNullOrBlank() || pass.text.isNullOrBlank()) {
                toast(label("ဖုန်းနံပါတ်နဲ့ စကားဝှက် ဖြည့်ပါ", "กรุณากรอกเบอร์โทรศัพท์และรหัสผ่าน"))
            } else showUserHome(phone.text.toString())
        }
        button(root, label("Demo User အသစ်ဖွင့်ရန်", "สมัครผู้ใช้ทดลอง")) { showRegister() }
        button(root, "Admin Login") { showAdminLogin() }
        setContentView(root)
    }

    private fun showRegister() {
        val root = baseLayout()
        heading(root, label("User အကောင့်ဖွင့်ရန်", "สมัครบัญชีผู้ใช้"))
        val phone = field(label("ဖုန်းနံပါတ်", "เบอร์โทรศัพท์"))
        val pass = field(label("စကားဝှက်", "รหัสผ่าน"), true)
        root.addView(phone); root.addView(pass)
        button(root, label("Demo အကောင့်တောင်းမည်", "ส่งคำขอบัญชีทดลอง")) {
            if (phone.text.isNullOrBlank() || pass.text.isNullOrBlank()) {
                toast(label("အချက်အလက်ဖြည့်ပါ", "กรุณากรอกข้อมูล"))
            } else {
                toast(label("ဒီစမ်းသပ်ဗားရှင်းမှာ အကောင့်တောင်းဆိုမှုကို မသိမ်းဆည်းပါ။ Online approval မချိတ်ဆက်ရသေးပါ။",
                    "รุ่นทดลองนี้ยังไม่บันทึกคำขอ และยังไม่ได้เชื่อมต่อระบบอนุมัติออนไลน์"))
                showLogin()
            }
        }
        button(root, label("နောက်သို့", "ย้อนกลับ")) { showLogin() }
        setContentView(root)
    }

    private fun showAdminLogin() {
        val root = baseLayout()
        heading(root, "ADMIN LOGIN")
        val user = field("Admin Username")
        user.setText("admin")
        root.addView(user)
        val pass = field(label("Admin Password", "รหัสผ่านผู้ดูแล"), true)
        root.addView(pass)
        button(root, label("ဝင်မည်", "เข้าสู่ระบบ")) {
            toast(label("Admin authentication ကို လုံခြုံတဲ့ backend မှာ ချိတ်ဆက်ပြီးမှ အသုံးပြုနိုင်ပါမယ်။",
                "ต้องเชื่อมต่อการยืนยันตัวตนผู้ดูแลกับ backend ที่ปลอดภัยก่อนใช้งาน"))
        }
        button(root, label("နောက်သို့", "ย้อนกลับ")) { showLogin() }
        setContentView(root)
    }

    private fun showUserHome(phone: String) {
        val root = baseLayout()
        heading(root, "🍀 LUCKY 2D")
        root.addView(TextView(this).apply {
            text = label("မင်္ဂလာပါ • $phone", "สวัสดี • $phone")
            textSize = 16f; setTextColor(ink); gravity = Gravity.CENTER
        })
        root.addView(TextView(this).apply {
            text = label("00 မှ 99 Demo နံပါတ်ရွေးပါ", "เลือกหมายเลขทดลอง 00 ถึง 99")
            gravity = Gravity.CENTER; setTextColor(red); textSize = 16f
        })
        val scroll = ScrollView(this)
        val grid = GridLayout(this).apply { columnCount = 5 }
        for (n in 0..99) {
            val number = n.toString().padStart(2, '0')
            val b = Button(this).apply {
                text = number
                textSize = 13f
                setTextColor(if (selectedNumber == number) Color.WHITE else red)
                setBackgroundColor(if (selectedNumber == number) red else Color.rgb(255, 230, 220))
                setOnClickListener { selectedNumber = number; showUserHome(phone) }
            }
            grid.addView(b, GridLayout.LayoutParams().apply {
                width = 0; height = 48
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                setMargins(2, 2, 2, 2)
            })
        }
        scroll.addView(grid)
        root.addView(scroll, LinearLayout.LayoutParams(-1, 0, 1f))
        root.addView(TextView(this).apply {
            text = label("ရွေးထားသော Demo နံပါတ်: ${selectedNumber ?: "မရွေးရသေး"}",
                "หมายเลขทดลองที่เลือก: ${selectedNumber ?: "ยังไม่ได้เลือก"}")
            gravity = Gravity.CENTER; setTextColor(ink); textSize = 16f
        })
        button(root, label("Demo စလစ်ဖန်တီးမည်", "สร้างสลิปทดลอง")) {
            val num = selectedNumber
            if (num == null) toast(label("နံပါတ်ရွေးပါ", "กรุณาเลือกหมายเลข"))
            else {
                val stamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
                val id = "LK-DEMO-${System.currentTimeMillis()}"
                records.add("$id | $num | $stamp | $phone")
                showSlip(id, num, stamp, phone)
            }
        }
        button(root, label("Demo မှတ်တမ်း", "ประวัติทดลอง")) { showRecords() }
        button(root, label("ထွက်မည်", "ออกจากระบบ")) { selectedNumber = null; showLogin() }
        setContentView(root)
    }

    private fun showSlip(id: String, number: String, stamp: String, phone: String) {
        val root = baseLayout()
        heading(root, "🍀 LUCKY 2D")
        root.addView(TextView(this).apply {
            text = label("DEMO စလစ်", "สลิปทดลอง")
            textSize = 20f; typeface = Typeface.DEFAULT_BOLD; gravity = Gravity.CENTER; setTextColor(red)
        })
        val details = listOf(
            "${label("မှတ်တမ်းအမှတ်", "รหัสรายการ")}: $id",
            "${label("နံပါတ်", "หมายเลข")}: $number",
            "${label("ရက်စွဲ/အချိန်", "วันที่/เวลา")}: $stamp",
            "${label("User ဖုန်း", "เบอร์ผู้ใช้")}: $phone",
            "QR demo token: $id"
        ).joinToString("\n\n")
        root.addView(TextView(this).apply {
            text = details; textSize = 16f; setTextColor(ink); setPadding(10, 24, 10, 24)
        })
        root.addView(TextView(this).apply {
            text = "DEMO ONLY • NO BETTING • NO PAYMENT"
            textSize = 13f; gravity = Gravity.CENTER; setTextColor(red); typeface = Typeface.DEFAULT_BOLD
        })
        button(root, label("မှတ်တမ်းသို့", "ไปที่ประวัติ")) { showRecords() }
        button(root, label("ပင်မစာမျက်နှာ", "หน้าหลัก")) { showUserHome(phone) }
        setContentView(root)
    }

    private fun showRecords() {
        val root = baseLayout()
        heading(root, label("Demo မှတ်တမ်း", "ประวัติทดลอง"))
        if (records.isEmpty()) {
            root.addView(TextView(this).apply {
                text = label("မှတ်တမ်းမရှိသေးပါ", "ยังไม่มีประวัติ")
                gravity = Gravity.CENTER; setTextColor(ink)
            })
        } else records.reversed().forEach { item ->
            root.addView(TextView(this).apply {
                text = item; textSize = 13f; setTextColor(ink); setPadding(4, 12, 4, 12)
            })
        }
        button(root, label("နောက်သို့", "ย้อนกลับ")) { showLogin() }
        setContentView(root)
    }

    private fun toast(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}
