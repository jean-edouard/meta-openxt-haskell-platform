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

do_configure() {
    ./configure --prefix=${prefix} --enable-shared
    echo "STANDARD_OPTS += \"-I${STAGING_INCDIR_NATIVE}\"" >> rts/ghc.mk
}
