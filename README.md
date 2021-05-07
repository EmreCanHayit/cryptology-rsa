# What is the RSA algorithm?
The RSA algorithm is an asymmetric cryptography algorithm; this means that it uses a public key and a private key (i.e two different, mathematically linked keys). As their names suggest, a public key is shared publicly, while a private key is secret and must not be shared with anyone.

# Working steps
* Choose two distinct primes p and q
* Compute bit length n = p * q
* Compute totient ϕ(phi) = (p - 1) * (q - 1)
* Choose a public exponent e
  * 1 < e < ϕ(phi)
  * gcd(e, ϕ(phi)) = 1
* Choose a prive exponent d
  * e.d = 1mod(ϕ(phi))
# Keys
* Public key (n, e)
* Private key (n, d)
# Encryption formule
C = P <sup>e</sup> mod(ϕ(phi))
# Decryption
P = C <sup>d</sup> mod(ϕ(phi))

# Output
```
p = 2441
q = 3659
--------------------------
n = 8931619
ϕ/phi = 8925520
--------------------------
Public key [8931619, 4431123]
Private key [8931619, 7161547]
--------------------------
Text       : Aşırı önemli gizli mesaj denemesi
Encryption : 呥胲ࢉ㖀궟砰ខ壊㖀ݝ뿽壊㖀ខ砰㲛猳ᛚ㖀牰砰궟砰ខ砰㲛
Decryption : Aşırı önemli gizli mesaj denemesi
```

# Different option
Limiting the public key can speed up the algorithm
``` Java
public static BigInteger findOpenE(BigInteger phi) {
  ArrayList<BigInteger> eArray = new ArrayList<>();
  BigInteger i, loopBypass, constantBypass;
  loopBypass = constantBypass = phi.divide(BigInteger.TEN);

  for (i = BigInteger.valueOf(2); i.compareTo(phi) < 0; i = i.add(BigInteger.ONE)) {
    if ((i.gcd(phi)).compareTo(BigInteger.ONE) == 0 && finiteSize(eArray.size(), 10)) {
      eArray.add(i);

      i = loopBypass(i, loopBypass);
      loopBypass = loopBypass.add(constantBypass);
      if (i.compareTo(loopBypass) <0){
        i = loopBypass;
      }
      loopBypass = loopBypass.add(constantBypass);
    }
  }
  return eArray.get(createRandom(eArray.size()));
}

public static BigInteger loopBypass(BigInteger i, BigInteger loopBypass) {
  if (i.compareTo(loopBypass) < 0) {
    return i = loopBypass;
  }
  return i;
}

public static boolean finiteSize(int eArraySize, int limit){
  return eArraySize < limit;
}
```

# Theory source
[DI Management](https://www.di-mgt.com.au/rsa_theory.html)

[Educative](https://www.educative.io/edpresso/what-is-the-rsa-algorithm)
