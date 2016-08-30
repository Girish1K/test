import java.util.Hashtable;
import java.util.Properties;

import javax.naming.*;
import javax.naming.directory.*;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;


public class SimpleLDAPClient {
    public static void main1(String[] args) {
        Hashtable env = new Hashtable();

        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://stplpdc.sciformix.auth:389/DC=SCIFORMIX,DC=AUTH");
    //    env.put(Context.SECURITY_AUTHENTICATION, "none");
        env.put(Context.SECURITY_PRINCIPAL, "scvadmin");
        
        env.put(Context.SECURITY_CREDENTIALS, "pass$123");
        DirContext ctx = null;
        NamingEnumeration results = null;
              try {
            ctx = new InitialDirContext(env);
            
            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            results = ctx.search("", "(objectclass=person)", controls);
            while (results.hasMore()) {
                SearchResult searchResult = (SearchResult) results.next();
                Attributes attributes = searchResult.getAttributes();
                Attribute attr = attributes.get("sAMAccountName");
                String cn = (String) attr.get();
                System.out.println(" Person Common Name = " + cn);
               // System.out.println(" Attributese = " + attributes.getAll());
                
                
            }
        } catch (NamingException e) {
            throw new RuntimeException(e);
        } finally {
            if (results != null) {
                try {
                    results.close();
                } catch (Exception e) {
                }
            }
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (Exception e) {
                }
            }
        }
    }
    
    public static void main(String[] args) {
    	//SimpleLDAPClient retrieveUserAttributes = new SimpleLDAPClient();
      //  retrieveUserAttributes.getUserBasicAttributes("scvadmin", retrieveUserAttributes.getLdapContext());
    	
    	         try {
    	        	 LdapContext conn=getConnection("sghuge", "paFSFSFss$123", "SCIFORMIX.AUTH", "stplpdc.sciformix.auth");
    	        	 System.out.println("SUCCESS"+conn);
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	   
    }
    
    
  //**************************************************************************
    //** getConnection
    //*************************************************************************/
    /** Used to authenticate a user given a username/password and domain name.
     *  Provides an option to identify a specific a Active Directory server.
     */
      public static LdapContext getConnection(String username, String password, String domainName, String serverName) throws NamingException {
   
          if (domainName==null){
              try{
                  String fqdn = java.net.InetAddress.getLocalHost().getCanonicalHostName();
                  if (fqdn.split("\\.").length>1) domainName = fqdn.substring(fqdn.indexOf(".")+1);
              }
              catch(java.net.UnknownHostException e){}
          }
           
          //System.out.println("Authenticating " + username + "@" + domainName + " through " + serverName);
   
          if (password!=null){
              password = password.trim();
              if (password.length()==0) password = null;
          }
   
          //bind by using the specified username/password
          Hashtable props = new Hashtable();
          String principalName = username + "@" + domainName;
          props.put(Context.SECURITY_PRINCIPAL, principalName);
          if (password!=null) props.put(Context.SECURITY_CREDENTIALS, password);
   
   
          String ldapURL = "ldap://stplpdc.sciformix.auth:389/DC=SCIFORMIX,DC=AUTH";
          props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
          props.put(Context.PROVIDER_URL, ldapURL);
          try{
              return new InitialLdapContext(props, null);
          }
          catch(javax.naming.CommunicationException e){
              throw new NamingException("Failed to connect to " + domainName + ((serverName==null)? "" : " through " + serverName));
          }
          catch(NamingException e){
              throw new NamingException("Failed to authenticate " + username + "@" + domainName + ((serverName==null)? "" : " through " + serverName));
          }
      }
   
    
    public LdapContext getLdapContext(){
        LdapContext ctx = null;
        try{
            Hashtable<String, String> env = new Hashtable<String, String>();
        //    env.put(Context.SECURITY_AUTHENTICATION, "Simple");
           
          
            env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, "ldap://stplpdc.sciformix.auth:389/DC=SCIFORMIX,DC=AUTH");
        //    env.put(Context.SECURITY_AUTHENTICATION, "none");
            env.put(Context.SECURITY_PRINCIPAL, "scvadmin");
            env.put(Context.SECURITY_CREDENTIALS, "pass$123");
            ctx = new InitialLdapContext(env, null);
            
            System.out.println("Connection Successful.");
        }catch(NamingException nex){
            System.out.println("LDAP Connection: FAILED");
            nex.printStackTrace();
        }
        return ctx;
        
    }
 
    
    
    private boolean getUserBasicAttributes(String username, LdapContext ctx) {
     
        try {
 
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String[] attrIDs = { "distinguishedName",
                    "sn",
                    "givenname",
                    "mail",
                    "telephonenumber"};
            constraints.setReturningAttributes(attrIDs);
            //First input parameter is search bas, it can be "CN=Users,DC=YourDomain,DC=com"
            //Second Attribute can be uid=username
            NamingEnumeration answer = ctx.search("DC=SCIFORMIX,DC=AUTH", "sAMAccountName="+ username, constraints);
            if (answer.hasMore()) {
                Attributes attrs = ((SearchResult) answer.next()).getAttributes();
             //   System.out.println("distinguishedName "+ attrs.get("distinguishedName"));
               System.out.println("userId "+ attrs.get("userId"));
           //    System.out.println("sn "+ attrs.get("sn"));
            //   System.out.println("mail "+ attrs.get("mail"));
           //    System.out.println("telephonenumber "+ attrs.get("telephonenumber"));
            }else{
                throw new Exception("Invalid User");
            }
 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }
}