package tcb.bcms.manipulation;

import java.security.ProtectionDomain;

public interface IClassTransformer { 
	public byte[] transform(ClassLoader classLoader, String className, Class<?> paramClass, ProtectionDomain protectionDomain, byte[] bytecode);
}
