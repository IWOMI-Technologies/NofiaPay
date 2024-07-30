//package com.iwomi.nofiaPay.frameworks.data.entities;
//
//import jakarta.persistence.Entity;
//import lombok.*;
//import org.hibernate.annotations.SQLDelete;
//import org.hibernate.annotations.Where;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@Builder(toBuilder = true)
//@Entity(name = "users")
//@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id=?")
//@Where(clause = "deleted=false")
//public class ClientEntity extends BaseEntity  {
//    @Id
//    private String etab;//code etablissement
//
//    private String cli;//code identifiant cl
//    @Id
//    private String tel;//tel cl pr connexion
//    private String ema;//email du client
//    private String nom;// nom ou raison sociale du client
//    private String npid;//numero piece identification
//    private String tpid;//type de piece didentification
//    private String tprod;//type de produit a souscrir, 0= USSD, 1=Web banking et 2=Mobile Banking
//    private String prof;//profil du client
//    private String brch;//agence du client
//    private String codewal;//identifiant unique wallet client
//    private String typecli;//type de client / marchand=mar/simple client =cli
//    private String codecli;//alias du client unique par client
//    private String typewal;//Wallet principal=walp ou sous wallet=wals/
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    @Temporal(TemporalType.DATE)
//    private Date ddel; //date de delivrance
//    private String ldel;//lieu de delivrance
//    private String adel;//Autorité ayant délivré
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    @Temporal(TemporalType.DATE)
//    private Date dexp; //date expiration doc client
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    @Temporal(TemporalType.DATE)
//    private Date dnai;//date de naissance
//    private String lnai;//lieu de naissance
//
//    private Activationstatus activationstatut; // attribute for activation and deactivation of client
//    private String rccm;//registre de commence
//    private String ncc;//numero du contribuable
//    private Date dcre;// date de creation etablissement
//    private String sig;//0=otp, 1=pin 2=pin ok
//    private String adr;//adrese du client
//    private String tel2;// telephone du client 237677777777
//    private String chl1; //
//    private String chl2;//champ libre 2
//    private String chl3;//champ libre 3
//    private String chl4;//champ libre 4
//    private String chl5;//champ libre 5
//    private String chl6;//typze
//    private String pin;//Pin lor de la creation du clien
//    private String otp;//One tme password
//    private String eta;//etat de traitement
//    private String niv;//niveau de traitement
//    private String uti;//user create
//    private String utimo;//user update
//    private String modu;//module
//    private String refcli;//module
//    private LocalDateTime dou1;//date modification
//    private Timestamp dou;//date modification
//    private Timestamp dmo;//date creation
//    private String pwd;//user password
//    private String dele;//statut suprimer 0 existe et 1= supprimmer
//    private int cpin;//compter le pin
//    private int cotp;//compter otp
//    private int ccon;//compter le nombre de connexion
//    private String isfirst;//0=premiere connection et 1 deja connecter
//    private String iupwd;//initier validation pwd 0=initialisation activer, 1 ou null non activer
//    private String iupin;//initier validation pin 0=initialisation activer, 1 ou null non activer
//    private String codeimei;//code emei
//    private String stap;//0=piece deja joint, 1 ou null piece non jointe
//    private String libpj;//Libelle piece jointe
//    private String libph;//Libelle piece jointe photo
//    private Integer trialNumber;    // for questionnaire trials
//
//    private boolean deleted = Boolean.FALSE;
//}
