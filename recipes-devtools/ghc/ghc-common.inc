DESCRIPTION = "The Glasgow Haskell Compiler."
HOMEPAGE = "https://www.haskell.org/ghc/download_ghc_6_12_3"
LICENSE = "GHCL"
SECTION = "devel/haskell"

DEPENDS += " \
    binutils \
"

S = "${WORKDIR}/ghc-${PV}"

CFLAGS_append = " -no-pie "
LDFLAGS_append = " -no-pie "
CONF_CC_OPTS_append = " -no-pie "
CONF_LD_OPTS_append = " -no-pie "
