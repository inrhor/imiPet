package cn.inrhor.imipet.utlis

class UnicodeUtil {
    /**
     * 将字符串转成unicode
     * @param str 待转字符串
     * @return unicode字符串
     */
    fun convert(str: String): String? {
        var tmp: String
        val sb = StringBuffer(1000)
        var c: Char
        var j: Int
        sb.setLength(0)
        var i = 0
        while (i < str.length) {
            c = str[i]
            sb.append("\\u")
            j = c.toInt() ushr 8 //取出高8位
            tmp = Integer.toHexString(j)
            if (tmp.length == 1) sb.append("0")
            sb.append(tmp)
            j = c.toInt() and 0xFF //取出低8位
            tmp = Integer.toHexString(j)
            if (tmp.length == 1) sb.append("0")
            sb.append(tmp)
            i++
        }
        return String(sb)
    }
}