inherit native
require ghc-${PV}.inc

# This requires a ghc6 capable compiler to be already installed on the host in
# order to bootstrap the build.
# ghc-6.12.3 can be found: https://www.haskell.org/ghc/download_ghc_6_12_3.html
# Get the binary package as it is not trivial to get it to build as intended, I
# also recommend
# http://www.edsko.net/2013/02/10/comprehensive-haskell-sandboxes/ to separate
# the installation from the host rootfs.
#
# Static libraries are not -fPIC, This makes x86_64 attempts to build the
# compiler fail miserably, example using the binary package with an HelloWord:
#
#   Hello.hs:main = putStrLn "Hello, World!"
#
#   $ ghc -dynamic -o hello Hello.hs
#   /usr/bin/ld: warning: libgmp.so.3, needed by /home/user/.ghc-env/ghc6-x86_64/local//lib/ghc-6.12.3/haskell98-1.0.1.1/libHShaskell98-1.0.1.1-ghc6.12.3.so, may conflict with libgmp.so.10
#   /usr/bin/ld: /home/user/.ghc-env/ghc6-x86_64/local//lib/ghc-6.12.3/libHSrtsmain.a(Main.o): relocation R_X86_64_32 against symbol `ZCMain_main_closure' can not be used when making a shared object; recompile with -fPIC
#   /usr/bin/ld: Hello.o: relocation R_X86_64_PC32 against symbol `newCAF' can not be used when making a shared object; recompile with -fPIC
#   /usr/bin/ld: final link failed: Bad value
#   collect2: error: ld returned 1 exit status
#
# This will have to be dealt with later.

TARGET_CFLAGS_append = " -no-pie "
NATIVE_CFLAGS_append = " -no-pie "
TARGET_LDFLAGS_append = " -no-pie "
NATIVE_LDFLAGS_append = " -no-pie "
CFLAGS_append = " -no-pie "
LDFLAGS_append = " -no-pie "
CONF_CC_OPTS_append = " -no-pie "
CONF_LD_OPTS_append = " -no-pie "
TUNE_CCARGS_append = "-no-pie"

TARGET_CFLAGS_remove = "-pie"
NATIVE_CFLAGS_remove = "-pie"
TARGET_LDFLAGS_remove = "-pie"
NATIVE_LDFLAGS_remove = "-pie"
CFLAGS_remove = "-pie"
LDFLAGS_remove = "-pie"
CONF_CC_OPTS_remove = "-pie"
CONF_LD_OPTS_remove = "-pie"
TUNE_CCARGS_remove = "-pie"


do_configure() {
    export CFLAGS="$CFLAGS -no-pie"
    export LDFLAGS="$LDFLAGS -no-pie"
    export BUILD_CFLAGS="$BUILD_CFLAGS -no-pie"
    export BUILD_LDFLAGS="$BUILD_LDFLAGS -no-pie"
    ./configure --prefix=${prefix} --enable-shared \
    CONF_GCC_LINKER_OPTS_STAGE0=-no-pie \
    CONF_LD_LINKER_OPTS_STAGE0=-no-pie \
    CONF_HC_OPTS_STAGE0=-optl=-no-pie
    CONF_CC_OPTS_STAGE2=-fno-PIE \
    CONF_GCC_LINKER_OPTS_STAGE2=-no-pie \
    CONF_LD_LINKER_OPTS_STAGE2=-no-pie

    echo "STANDARD_OPTS += \"-I${STAGING_INCDIR_NATIVE}\"" >> rts/ghc.mk
}

#SECURITY_PIE_CFLAGS_remove = "-fPIE -pie --enable-default-pie"
GCCPIE = ""
SECURITY_PIE_CFLAGS = ""
