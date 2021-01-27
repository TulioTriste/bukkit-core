package com.lunarclient.bukkitapi.nethandler.client.obj;

public enum Emote
{
    FRESH(7, "Fresco"), 
    HYPE(8, "Hype"), 
    SQUAT_KICK(9, "Tripaloski"), 
    L_DANCE(10, "Baile de la L"), 
    TIDY(11, "Snoop Dog"), 
    FREE_FLOW(12, "Flow libre"), 
    SHIMMER(13, "Viejito"), 
    GET_FUNKY(14, "Cadereo"), 
    GUN_LEAN(15, "La lagartija"), 
    GANGAM_STYLE(16, "Gangam Style"), 
    SALUTE(17, "A la orden"), 
    BITCHSLAP(18, "Me cachis"), 
    BONGO_CAT(19, "Berrinche"), 
    BREATHTAKING(20, "Ooooleeee"), 
    DISGUSTED(21, "Mareado"), 
    EXHAUSTED(22, "Exhausto"), 
    PUNCH(23, "Golpear"), 
    SNEEZE(24, "Estornudo"), 
    THREATENING(25, "Te rajo"), 
    WOAH(26, "WOAH"), 
    BONELESS(27, "Deshuesado"), 
    BEST_MATES(28, "Bailongo"), 
    DEFAULT(29, "Fornait"), 
    DISCO_FEVER(30, "Siempre disco"), 
    ELECTRO_SHUFFLE(31, "Shuffle electrico"), 
    FLOSS_2(32, "Floss 2"), 
    INFINITE_DAB(33, "Dab infinito"), 
    ORANGE_JUSITCE(34, "Justicia naranja"), 
    SKIBIDI(35, "Skibidi"), 
    BOY(36, "OOOH SIII"), 
    BOW(37, "Saludo oriental"), 
    CALCULATED(38, "Calculado"), 
    CHICKEN(39, "Gallina"), 
    CLAPPING(40, "Aplaudir"), 
    CLUB(41, "Baile de Club"), 
    CONFUSED(42, "Confundido"), 
    CRYING(43, "Llorar"), 
    DAB_2(44, "Dab 2"), 
    FACEPALM_2(45, "Facepalm 2"), 
    FIST(46, "Primero"), 
    LAUGHING(47, "Reirse"), 
    NO(48, "No"), 
    POINTING(49, "Se\u00f1alar"), 
    POPCORN(50, "Palomitas"), 
    PURE_SALT(51, "Sal"), 
    SHRUG_2(52, "Ni idea"), 
    T_POSE_2(53, "T-POSE 2"), 
    THINKING(54, "Pensado"), 
    TWERK(55, "Twerk"), 
    WAVE_2(56, "Saludar 2"), 
    YES(57, "S\u00ed");
    
    private final int emoteId;
    private final String name;
    
    Emote(final int emoteId, final String name) {
        this.emoteId = emoteId;
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getPermission() {
        return "emote." + this.name().toLowerCase();
    }
    
    public int getEmoteId() {
        return this.emoteId;
    }
    
    public static Emote getById(final int emoteId) {
        for (final Emote emote : values()) {
            if (emote.getEmoteId() == emoteId) {
                return emote;
            }
        }
        return null;
    }
    
    public static Emote getByName(final String input) {
        for (final Emote emote : values()) {
            if (emote.name().equalsIgnoreCase(input)) {
                return emote;
            }
        }
        return null;
    }
}
