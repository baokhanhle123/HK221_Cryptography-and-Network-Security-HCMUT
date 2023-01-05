import java.io.*;
import java.util.*;
import java.security.*;
import javax.crypto.*;
class listAllAlgorithms {
   public static void main(String[] a) {
		
		System.out.println("List all algorithms of Signature service:");
		String[] names = getCryptoImpls("Signature", "SUN");
		for(int i =0; i < names.length; i++)
			System.out.println("Algorithm[" + i + "]:" + names[i]);
		System.out.println("//////////////////////////////////////////////");
		System.out.println("List all algorithms of MessageDigest service:");
		names = getCryptoImpls("MessageDigest", "SUN");
		for(int i =0; i < names.length; i++)
			System.out.println("Algorithm[" + i + "]:" + names[i]);	
		System.out.println("//////////////////////////////////////////////");
		System.out.println("List all algorithms of Cipher service:");
		names = getCryptoImpls("Cipher", "SUN");
		for(int i =0; i < names.length; i++)
			System.out.println("Algorithm[" + i + "]:" + names[i]);	
		System.out.println("//////////////////////////////////////////////");
		System.out.println("List all algorithms of Mac service:");
		names = getCryptoImpls("Mac", "SUN");
		for(int i =0; i < names.length; i++)
			System.out.println("Algorithm[" + i + "]:" + names[i]);
			System.out.println("//////////////////////////////////////////////");
		System.out.println("List all algorithms of KeyStore service:");
		names = getCryptoImpls("KeyStore", "SUN");
		for(int i =0; i < names.length; i++)
			System.out.println("Algorithm[" + i + "]:" + names[i]);
   }
   
   // This method returns all available services types
	public static String[] getServiceTypes(String ProviderName) {
		Set result = new HashSet();

		Provider provider = Security.getProvider(ProviderName);
        Set keys = provider.keySet();
        for (Iterator it=keys.iterator(); it.hasNext(); ) {
            String key = (String)it.next();
            key = key.split(" ")[0];
            if (key.startsWith("Alg.Alias.")) {
                // Strip the alias
                key = key.substring(10);
            }
            int ix = key.indexOf('.');
            result.add(key.substring(0, ix));
        }
		return (String[])result.toArray(new String[result.size()]);
	}

	// This method returns the available implementations for a service type
	public static String[] getCryptoImpls(String serviceType, String ProviderName) {
		Set result = new HashSet();

		// All all providers
		Provider provider = Security.getProvider(ProviderName);
		Set keys = provider.keySet();
		for (Iterator it=keys.iterator(); it.hasNext(); ) {
			String key = (String)it.next();
			key = key.split(" ")[0];
			if (key.startsWith(serviceType+".")) {
				result.add(key.substring(serviceType.length()+1));
			} else if (key.startsWith("Alg.Alias."+serviceType+".")) {
				// This is an alias
				result.add(key.substring(serviceType.length()+11));
			}
		}
		return (String[])result.toArray(new String[result.size()]);
	}
}