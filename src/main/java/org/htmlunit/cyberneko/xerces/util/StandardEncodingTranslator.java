/*
 * Copyright (c) 2017-2024 Ronald Brill
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.htmlunit.cyberneko.xerces.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * EncodingTranslator based on <a href='https://encoding.spec.whatwg.org/#names-and-labels'>
 * https://encoding.spec.whatwg.org/#names-and-labels</a>
 *
 * @author Ronald Brill
 */
public final class StandardEncodingTranslator implements EncodingTranslator {

    /**
     * Singleton.
     */
    public static final StandardEncodingTranslator INSTANCE = new StandardEncodingTranslator();

    /** <a href="https://encoding.spec.whatwg.org/#names-and-labels">Encoding names and labels</a> */
    private static final Map<String, String> ENCODING_FROM_LABEL;

    static {
        ENCODING_FROM_LABEL = new HashMap<>();

        // The Encoding
        // ------------
        ENCODING_FROM_LABEL.put("unicode-1-1-utf-8", "utf-8");
        ENCODING_FROM_LABEL.put("unicode11utf8", "utf-8");
        ENCODING_FROM_LABEL.put("unicode20utf8", "utf-8");
        ENCODING_FROM_LABEL.put("utf-8", "utf-8");
        ENCODING_FROM_LABEL.put("utf8", "utf-8");
        ENCODING_FROM_LABEL.put("x-unicode20utf8", "utf-8");

        // Legacy single-byte encodings
        // ----------------------------

        // ibm866
        ENCODING_FROM_LABEL.put("866", "ibm866");
        ENCODING_FROM_LABEL.put("cp866", "ibm866");
        ENCODING_FROM_LABEL.put("csibm866", "ibm866");
        ENCODING_FROM_LABEL.put("ibm866", "ibm866");

        // iso-8859-2
        ENCODING_FROM_LABEL.put("csisolatin2", "iso-8859-2");
        ENCODING_FROM_LABEL.put("iso-8859-2", "iso-8859-2");
        ENCODING_FROM_LABEL.put("iso-ir-101", "iso-8859-2");
        ENCODING_FROM_LABEL.put("iso8859-2", "iso-8859-2");
        ENCODING_FROM_LABEL.put("iso88592", "iso-8859-2");
        ENCODING_FROM_LABEL.put("iso_8859-2", "iso-8859-2");
        ENCODING_FROM_LABEL.put("iso_8859-2:1987", "iso-8859-2");
        ENCODING_FROM_LABEL.put("l2", "iso-8859-2");
        ENCODING_FROM_LABEL.put("latin2", "iso-8859-2");

        // iso-8859-3
        ENCODING_FROM_LABEL.put("csisolatin3", "iso-8859-3");
        ENCODING_FROM_LABEL.put("iso-8859-3", "iso-8859-3");
        ENCODING_FROM_LABEL.put("iso-ir-109", "iso-8859-3");
        ENCODING_FROM_LABEL.put("iso8859-3", "iso-8859-3");
        ENCODING_FROM_LABEL.put("iso88593", "iso-8859-3");
        ENCODING_FROM_LABEL.put("iso_8859-3", "iso-8859-3");
        ENCODING_FROM_LABEL.put("iso_8859-3:1988", "iso-8859-3");
        ENCODING_FROM_LABEL.put("l3", "iso-8859-3");
        ENCODING_FROM_LABEL.put("latin3", "iso-8859-3");

        // iso-8859-4
        ENCODING_FROM_LABEL.put("csisolatin4", "iso-8859-4");
        ENCODING_FROM_LABEL.put("iso-8859-4", "iso-8859-4");
        ENCODING_FROM_LABEL.put("iso-ir-110", "iso-8859-4");
        ENCODING_FROM_LABEL.put("iso8859-4", "iso-8859-4");
        ENCODING_FROM_LABEL.put("iso88594", "iso-8859-4");
        ENCODING_FROM_LABEL.put("iso_8859-4", "iso-8859-4");
        ENCODING_FROM_LABEL.put("iso_8859-4:1988", "iso-8859-4");
        ENCODING_FROM_LABEL.put("l4", "iso-8859-4");
        ENCODING_FROM_LABEL.put("latin4", "iso-8859-4");

        // iso-8859-5
        ENCODING_FROM_LABEL.put("csisolatincyrillic", "iso-8859-5");
        ENCODING_FROM_LABEL.put("cyrillic", "iso-8859-5");
        ENCODING_FROM_LABEL.put("iso-8859-5", "iso-8859-5");
        ENCODING_FROM_LABEL.put("iso-ir-144", "iso-8859-5");
        ENCODING_FROM_LABEL.put("iso8859-5", "iso-8859-5");
        ENCODING_FROM_LABEL.put("iso88595", "iso-8859-5");
        ENCODING_FROM_LABEL.put("iso_8859-5", "iso-8859-5");
        ENCODING_FROM_LABEL.put("iso_8859-5:1988", "iso-8859-5");

        // iso-8859-6
        ENCODING_FROM_LABEL.put("arabic", "iso-8859-6");
        ENCODING_FROM_LABEL.put("asmo-708", "iso-8859-6");
        ENCODING_FROM_LABEL.put("csiso88596e", "iso-8859-6");
        ENCODING_FROM_LABEL.put("csiso88596i", "iso-8859-6");
        ENCODING_FROM_LABEL.put("csisolatinarabic", "iso-8859-6");
        ENCODING_FROM_LABEL.put("ecma-114", "iso-8859-6");
        ENCODING_FROM_LABEL.put("iso-8859-6", "iso-8859-6");
        ENCODING_FROM_LABEL.put("iso-8859-6-e", "iso-8859-6");
        ENCODING_FROM_LABEL.put("iso-8859-6-i", "iso-8859-6");
        ENCODING_FROM_LABEL.put("iso-ir-127", "iso-8859-6");
        ENCODING_FROM_LABEL.put("iso8859-6", "iso-8859-6");
        ENCODING_FROM_LABEL.put("iso88596", "iso-8859-6");
        ENCODING_FROM_LABEL.put("iso_8859-6", "iso-8859-6");
        ENCODING_FROM_LABEL.put("iso_8859-6:1987", "iso-8859-6");

        // iso-8859-7
        ENCODING_FROM_LABEL.put("csisolatingreek", "iso-8859-7");
        ENCODING_FROM_LABEL.put("ecma-118", "iso-8859-7");
        ENCODING_FROM_LABEL.put("elot_928", "iso-8859-7");
        ENCODING_FROM_LABEL.put("greek", "iso-8859-7");
        ENCODING_FROM_LABEL.put("greek8", "iso-8859-7");
        ENCODING_FROM_LABEL.put("iso-8859-7", "iso-8859-7");
        ENCODING_FROM_LABEL.put("iso-ir-126", "iso-8859-7");
        ENCODING_FROM_LABEL.put("iso8859-7", "iso-8859-7");
        ENCODING_FROM_LABEL.put("iso88597", "iso-8859-7");
        ENCODING_FROM_LABEL.put("iso_8859-7", "iso-8859-7");
        ENCODING_FROM_LABEL.put("iso_8859-7:1987", "iso-8859-7");
        ENCODING_FROM_LABEL.put("sun_eu_greek", "iso-8859-7");

        // iso-8859-8
        ENCODING_FROM_LABEL.put("csiso88598e", "iso-8859-8");
        ENCODING_FROM_LABEL.put("csisolatinhebrew", "iso-8859-8");
        ENCODING_FROM_LABEL.put("hebrew", "iso-8859-8");
        ENCODING_FROM_LABEL.put("iso-8859-8", "iso-8859-8");
        ENCODING_FROM_LABEL.put("iso-8859-8-e", "iso-8859-8");
        ENCODING_FROM_LABEL.put("iso-ir-138", "iso-8859-8");
        ENCODING_FROM_LABEL.put("iso8859-8", "iso-8859-8");
        ENCODING_FROM_LABEL.put("iso88598", "iso-8859-8");
        ENCODING_FROM_LABEL.put("iso_8859-8", "iso-8859-8");
        ENCODING_FROM_LABEL.put("iso_8859-8:1988", "iso-8859-8");
        ENCODING_FROM_LABEL.put("visual", "iso-8859-8");

        // iso-8859-8-i
        ENCODING_FROM_LABEL.put("csiso88598i", "iso-8859-8-i");
        ENCODING_FROM_LABEL.put("iso-8859-8-i", "iso-8859-8-i");
        ENCODING_FROM_LABEL.put("logical", "iso-8859-8-i");

        // iso-8859-10
        ENCODING_FROM_LABEL.put("csisolatin6", "iso-8859-10");
        ENCODING_FROM_LABEL.put("iso-8859-10", "iso-8859-10");
        ENCODING_FROM_LABEL.put("iso-ir-157", "iso-8859-10");
        ENCODING_FROM_LABEL.put("iso8859-10", "iso-8859-10");
        ENCODING_FROM_LABEL.put("iso885910", "iso-8859-10");
        ENCODING_FROM_LABEL.put("l6", "iso-8859-10");
        ENCODING_FROM_LABEL.put("latin6", "iso-8859-10");

        // iso-8859-13
        ENCODING_FROM_LABEL.put("iso-8859-13", "iso-8859-13");
        ENCODING_FROM_LABEL.put("iso8859-13", "iso-8859-13");
        ENCODING_FROM_LABEL.put("iso885913", "iso-8859-13");

        // iso-8859-14
        ENCODING_FROM_LABEL.put("iso-8859-14", "iso-8859-14");
        ENCODING_FROM_LABEL.put("iso8859-14", "iso-8859-14");
        ENCODING_FROM_LABEL.put("iso885914", "iso-8859-14");

        // iso-8859-15
        ENCODING_FROM_LABEL.put("csisolatin9", "iso-8859-15");
        ENCODING_FROM_LABEL.put("iso-8859-15", "iso-8859-15");
        ENCODING_FROM_LABEL.put("iso8859-15", "iso-8859-15");
        ENCODING_FROM_LABEL.put("iso885915", "iso-8859-15");
        ENCODING_FROM_LABEL.put("iso_8859-15", "iso-8859-15");
        ENCODING_FROM_LABEL.put("l9", "iso-8859-15");

        // iso-8859-16
        ENCODING_FROM_LABEL.put("iso-8859-16", "iso-8859-16");

        // koi8-r
        ENCODING_FROM_LABEL.put("cskoi8r", "koi8-r");
        ENCODING_FROM_LABEL.put("koi", "koi8-r");
        ENCODING_FROM_LABEL.put("koi8", "koi8-r");
        ENCODING_FROM_LABEL.put("koi8-r", "koi8-r");
        ENCODING_FROM_LABEL.put("koi8_r", "koi8-r");

        // koi8-u
        ENCODING_FROM_LABEL.put("koi8-u", "koi8-u");

        // macintosh
        ENCODING_FROM_LABEL.put("csmacintosh", "macintosh");
        ENCODING_FROM_LABEL.put("mac", "macintosh");
        ENCODING_FROM_LABEL.put("macintosh", "macintosh");
        ENCODING_FROM_LABEL.put("x-mac-roman", "macintosh");

        // windows-874
        ENCODING_FROM_LABEL.put("dos-874", "windows-874");
        ENCODING_FROM_LABEL.put("iso-8859-11", "windows-874");
        ENCODING_FROM_LABEL.put("iso8859-11", "windows-874");
        ENCODING_FROM_LABEL.put("iso885911", "windows-874");
        ENCODING_FROM_LABEL.put("tis-620", "windows-874");
        ENCODING_FROM_LABEL.put("windows-874", "windows-874");

        // windows-1250
        ENCODING_FROM_LABEL.put("cp1250", "windows-1250");
        ENCODING_FROM_LABEL.put("windows-1250", "windows-1250");
        ENCODING_FROM_LABEL.put("x-cp1250", "windows-1250");

        // windows-1251
        ENCODING_FROM_LABEL.put("cp1251", "windows-1251");
        ENCODING_FROM_LABEL.put("windows-1251", "windows-1251");
        ENCODING_FROM_LABEL.put("x-cp1251", "windows-1251");

        // windows-1252
        ENCODING_FROM_LABEL.put("ansi_x3.4-1968", "windows-1252");
        ENCODING_FROM_LABEL.put("ascii", "windows-1252");
        ENCODING_FROM_LABEL.put("cp1252", "windows-1252");
        ENCODING_FROM_LABEL.put("cp819", "windows-1252");
        ENCODING_FROM_LABEL.put("csisolatin1", "windows-1252");
        ENCODING_FROM_LABEL.put("ibm819", "windows-1252");
        ENCODING_FROM_LABEL.put("iso-8859-1", "windows-1252");
        ENCODING_FROM_LABEL.put("iso-ir-100", "windows-1252");
        ENCODING_FROM_LABEL.put("iso8859-1", "windows-1252");
        ENCODING_FROM_LABEL.put("iso88591", "windows-1252");
        ENCODING_FROM_LABEL.put("iso_8859-1", "windows-1252");
        ENCODING_FROM_LABEL.put("iso_8859-1:1987", "windows-1252");
        ENCODING_FROM_LABEL.put("l1", "windows-1252");
        ENCODING_FROM_LABEL.put("latin1", "windows-1252");
        ENCODING_FROM_LABEL.put("us-ascii", "windows-1252");
        ENCODING_FROM_LABEL.put("windows-1252", "windows-1252");
        ENCODING_FROM_LABEL.put("x-cp1252", "windows-1252");

        // windows-1253
        ENCODING_FROM_LABEL.put("cp1253", "windows-1253");
        ENCODING_FROM_LABEL.put("windows-1253", "windows-1253");
        ENCODING_FROM_LABEL.put("x-cp1253", "windows-1253");

        // windows-1254
        ENCODING_FROM_LABEL.put("cp1254", "windows-1254");
        ENCODING_FROM_LABEL.put("csisolatin5", "windows-1254");
        ENCODING_FROM_LABEL.put("iso-8859-9", "windows-1254");
        ENCODING_FROM_LABEL.put("iso-ir-148", "windows-1254");
        ENCODING_FROM_LABEL.put("iso8859-9", "windows-1254");
        ENCODING_FROM_LABEL.put("iso88599", "windows-1254");
        ENCODING_FROM_LABEL.put("iso_8859-9", "windows-1254");
        ENCODING_FROM_LABEL.put("iso_8859-9:1989", "windows-1254");
        ENCODING_FROM_LABEL.put("l5", "windows-1254");
        ENCODING_FROM_LABEL.put("latin5", "windows-1254");
        ENCODING_FROM_LABEL.put("windows-1254", "windows-1254");
        ENCODING_FROM_LABEL.put("x-cp1254", "windows-1254");

        // windows-1255
        ENCODING_FROM_LABEL.put("cp1255", "windows-1255");
        ENCODING_FROM_LABEL.put("windows-1255", "windows-1255");
        ENCODING_FROM_LABEL.put("x-cp1255", "windows-1255");

        // windows-1256
        ENCODING_FROM_LABEL.put("cp1256", "windows-1256");
        ENCODING_FROM_LABEL.put("windows-1256", "windows-1256");
        ENCODING_FROM_LABEL.put("x-cp1256", "windows-1256");

        // windows-1257
        ENCODING_FROM_LABEL.put("cp1257", "windows-1257");
        ENCODING_FROM_LABEL.put("windows-1257", "windows-1257");
        ENCODING_FROM_LABEL.put("x-cp1257", "windows-1257");

        // windows-1258
        ENCODING_FROM_LABEL.put("cp1258", "windows-1258");
        ENCODING_FROM_LABEL.put("windows-1258", "windows-1258");
        ENCODING_FROM_LABEL.put("x-cp1258", "windows-1258");

        // x-mac-cyrillic
        ENCODING_FROM_LABEL.put("x-mac-cyrillic", "x-mac-cyrillic");
        ENCODING_FROM_LABEL.put("x-mac-ukrainian", "x-mac-cyrillic");

        // Legacy multi-byte Chinese (simplified) encodings
        // ------------------------------------------------

        // gbk
        ENCODING_FROM_LABEL.put("chinese", "gbk");
        ENCODING_FROM_LABEL.put("csgb2312", "gbk");
        ENCODING_FROM_LABEL.put("csiso58gb231280", "gbk");
        ENCODING_FROM_LABEL.put("gb2312", "gbk");
        ENCODING_FROM_LABEL.put("gb_2312", "gbk");
        ENCODING_FROM_LABEL.put("gb_2312-80", "gbk");
        ENCODING_FROM_LABEL.put("gbk", "gbk");
        ENCODING_FROM_LABEL.put("iso-ir-58", "gbk");
        ENCODING_FROM_LABEL.put("x-gbk", "gbk");

        // gb18030
        ENCODING_FROM_LABEL.put("gb18030", "gb18030");

        // Legacy multi-byte Chinese (traditional) encodings
        // ------------------------------------------------

        // big5
        ENCODING_FROM_LABEL.put("big5", "big5");
        ENCODING_FROM_LABEL.put("big5-hkscs", "big5");
        ENCODING_FROM_LABEL.put("cn-big5", "big5");
        ENCODING_FROM_LABEL.put("csbig5", "big5");
        ENCODING_FROM_LABEL.put("x-x-big5", "big5");

        // Legacy multi-byte Japanese encodings
        // ------------------------------------

        // euc-jp
        ENCODING_FROM_LABEL.put("cseucpkdfmtjapanese", "euc-jp");
        ENCODING_FROM_LABEL.put("euc-jp", "euc-jp");
        ENCODING_FROM_LABEL.put("x-euc-jp", "euc-jp");

        // iso-2022-jp
        ENCODING_FROM_LABEL.put("csiso2022jp", "iso-2022-jp");
        ENCODING_FROM_LABEL.put("iso-2022-jp", "iso-2022-jp");

        // shift_jis
        ENCODING_FROM_LABEL.put("csshiftjis", "shift_jis");
        ENCODING_FROM_LABEL.put("ms932", "shift_jis");
        ENCODING_FROM_LABEL.put("ms_kanji", "shift_jis");
        ENCODING_FROM_LABEL.put("shift-jis", "shift_jis");
        ENCODING_FROM_LABEL.put("shift_jis", "shift_jis");
        ENCODING_FROM_LABEL.put("sjis", "shift_jis");
        ENCODING_FROM_LABEL.put("windows-31j", "shift_jis");
        ENCODING_FROM_LABEL.put("x-sjis", "shift_jis");

        // Legacy multi-byte Korean encodings
        // ------------------------------------

        // euc-kr
        ENCODING_FROM_LABEL.put("cseuckr", "euc-kr");
        ENCODING_FROM_LABEL.put("csksc56011987", "euc-kr");
        ENCODING_FROM_LABEL.put("euc-kr", "euc-kr");
        ENCODING_FROM_LABEL.put("iso-ir-149", "euc-kr");
        ENCODING_FROM_LABEL.put("korean", "euc-kr");
        ENCODING_FROM_LABEL.put("ks_c_5601-1987", "euc-kr");
        ENCODING_FROM_LABEL.put("ks_c_5601-1989", "euc-kr");
        ENCODING_FROM_LABEL.put("ksc5601", "euc-kr");
        ENCODING_FROM_LABEL.put("ksc_5601", "euc-kr");
        ENCODING_FROM_LABEL.put("windows-949", "euc-kr");

        // Legacy miscellaneous encodings
        // ------------------------------------

        // replacement
        ENCODING_FROM_LABEL.put("csiso2022kr", "replacement");
        ENCODING_FROM_LABEL.put("hz-gb-2312", "replacement");
        ENCODING_FROM_LABEL.put("iso-2022-cn", "replacement");
        ENCODING_FROM_LABEL.put("iso-2022-cn-ext", "replacement");
        ENCODING_FROM_LABEL.put("iso-2022-kr", "replacement");
        ENCODING_FROM_LABEL.put("replacement", "replacement");

        // utf-16be
        ENCODING_FROM_LABEL.put("unicodefffe", "utf-16be");
        ENCODING_FROM_LABEL.put("utf-16be", "utf-16be");

        // utf-16le
        ENCODING_FROM_LABEL.put("csunicode", "utf-16le");
        ENCODING_FROM_LABEL.put("iso-10646-ucs-2", "utf-16le");
        ENCODING_FROM_LABEL.put("ucs-2", "utf-16le");
        ENCODING_FROM_LABEL.put("unicode", "utf-16le");
        ENCODING_FROM_LABEL.put("unicodefeff", "utf-16le");
        ENCODING_FROM_LABEL.put("utf-16", "utf-16le");
        ENCODING_FROM_LABEL.put("utf-16le", "utf-16le");

        // x-user-defined
        ENCODING_FROM_LABEL.put("x-user-defined", "x-user-defined");
    }

    private StandardEncodingTranslator() {
    }

    /**
     * @return the Java encoding name for the specified IANA encoding name.
     *
     * @param ianaEncoding The IANA encoding name.
     */
    @Override
    public String encodingNameFromLabel(final String charsetLabel) {
        if (charsetLabel == null || charsetLabel.length() < 2) {
            return null;
        }
        String label = charsetLabel.trim().toLowerCase(Locale.ROOT);
        String ianaEncoding = ENCODING_FROM_LABEL.get(label);
        // Convert our IANA encoding names to Java charset names
        return EncodingMap.getIANA2JavaMapping(ianaEncoding);
    }
}
