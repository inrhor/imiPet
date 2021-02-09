package cn.mcres.imiPet.build.utils;

import cn.inrhor.imipet.ImiPet;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.bukkit.plugin.java.JavaPlugin;

import javax.script.*;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MathTool {
    /**
     * @param min
     * @param max
     * @return 两数之间的随机数
     */
    public static int random(int min, int max) {
        if (min > 0 && min != max) {
            Random random = new Random();
            return random.nextInt(max) % (max - min + 1) + min;
        } else {
            return max;
        }
    }

    /**
     * @param min
     * @param max
     * @return 两数之间的随机数
     */
    public static double random(double min, double max) {
        int scl =  2;
        int pow = (int) Math.pow(10, scl);
        return Math.floor((Math.random() * (max - min) + min) * pow) / pow;
    }

    public static double keepTwoDouble(double i) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return Double.parseDouble(decimalFormat.format(i));
    }

    /**
     * @param text
     * @param target
     * @return 获取指定字符的前面的字符串
     */
    public static String getTargetBefore(String text, String target) {
        int index = text.indexOf(target);
        return text.substring(0, index);
    }

    /**
     * @param text
     * @param target
     * @return 获取指定字符的后面的字符串
     */
    public static String getTargetAfter(String text, String target) {
        int index = text.indexOf(target);
        return text.substring(index + 1);
    }

    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> map(Map<K, V> source, Object... kv) {
        int i = 0;
        for (; i < kv.length; i += 2) {
            source.put((K) kv[i], (V) kv[i + 1]);
        }
        return source;
    }

    public static final ScriptEngine JavaScriptEngine = new ScriptEngineManager().getEngineByName("js");
    private static final Cache<String, CompiledScript> scripts = CacheBuilder.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES).build();
    private static final boolean CompileSupport;
    private static final Compilable COMPILABLE;
    private static final Object COMPILE_LOCK = new Object();

    static {
        if (CompileSupport = JavaScriptEngine instanceof Compilable) {
            COMPILABLE = (Compilable) JavaScriptEngine;
        } else COMPILABLE = null;
    }

    /**
     * @param string
     * @return 字符串转可运算的数学
     */
    public static double stringOperation(String string, Map<String, Object> vars) {
        return stringOperation(string, vars, null);
    }

    /**
     * @param string 代码
     * @param source 代码来源
     * @return 字符串转可运算的数学
     */
    public static double stringOperation(String string, Map<String, Object> vars, String source) {
        final Bindings bindings = JavaScriptEngine.createBindings(); // Create new Context.
        bindings.putAll(vars);

        ScriptInvokingCallback<Number> callback = new ScriptInvokingCallback<>();
        bindings.put("callback", callback);
        JavaPlugin plugin = ImiPet.loader.getPlugin();
        if (CompileSupport) {
            CompiledScript script = scripts.getIfPresent(string);
            if (script == null) {
                synchronized (COMPILE_LOCK) {
                    final ScriptContext context = JavaScriptEngine.getContext();
                    final SimpleScriptContext building = new SimpleScriptContext();
                    building.setAttribute(ScriptEngine.FILENAME, source, ScriptContext.ENGINE_SCOPE);
                    JavaScriptEngine.setContext(building);
                    try {
                        script = COMPILABLE.compile(string);
                        scripts.put(string, script);
                    } catch (Throwable e) {
                        plugin.getLogger().log(Level.SEVERE, "Error in parsing JavaScript CODE!");
                        plugin.getLogger().log(Level.SEVERE, string, e);
                        return 0;
                    } finally {
                        JavaScriptEngine.setContext(context);
                    }
                }
            }
            try {
                Object result = script.eval(bindings);
                if (callback.hasValue) return callback.value.doubleValue();
                return ((Number) result).doubleValue();
            } catch (Throwable e) {
                plugin.getLogger().log(Level.SEVERE, "Error in invoking JavaScript CODE!");
                plugin.getLogger().log(Level.SEVERE, string, e);
                return 0;
            }
        }
        try {
            ScriptContext context = new SimpleScriptContext();
            context.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
            context.setAttribute(ScriptEngine.FILENAME, source, ScriptContext.ENGINE_SCOPE);
            Object result = JavaScriptEngine.eval(string, context);
            if (callback.hasValue) return callback.value.doubleValue();
            return ((Number) result).doubleValue();
        } catch (Throwable e) {
            plugin.getLogger().log(Level.SEVERE, "Error in parsing JavaScript CODE!");
            plugin.getLogger().log(Level.SEVERE, string, e);
        }
        return 0;
    }

    public static double stringOperation(String string, String varName, Object value) {
        return stringOperation(string, Collections.singletonMap(varName, value));
    }

    /**
     * @param db
     * @return 四舍五入 double转int
     */
    public static int rounding(double db) {
        return Integer.parseInt(new java.text.DecimalFormat("0").format(db));
    }

    // 是否为大于0的数字，小数点最多两个（弃坑）
    /*public static boolean isNumber(String string) {
        Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");
        Matcher match = pattern.matcher(string);
        return match.matches();
    }*/

    /**
     * @param string
     * @return 是否为非零正整数数字
     */
    public static boolean isIntNumber(String string) {
        if (!string.equals("0")) {
            Pattern pattern = Pattern.compile("^\\+?[1-9][0-9]*$");
            Matcher matcher = pattern.matcher(string);
            return matcher.matches();
        }
        return false;
    }

    /**
     * @param string
     * @return 是否是正数，整数，小数
     */
    public static boolean isNumber(String string){
        String str = "^\\+?([0-9]+\\.?)?[0-9]+$";
        return string.matches(str);
    }
}
