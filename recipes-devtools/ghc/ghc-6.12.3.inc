require ghc-common.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI = " \
    https://downloads.haskell.org/~ghc/${PV}/ghc-${PV}-src.tar.bz2 \
    file://build.mk \
    file://gcc-4.9-fomit-frame-pointer.patch \
    file://use-order-only-constraints-for-directories.patch \
    file://linux-host-os-need-pthread.patch \
    file://binutils-2.24-config-guard.patch \
    file://jedpatch.patch \
    file://jedpatch2.patch \
"
SRC_URI[md5sum] = "4c2663c2eff833d7b9f39ef770eefbd6"
SRC_URI[sha256sum] = "6cbdbe415011f2c7d15e4d850758d8d393f70617b88cb3237d2c602bb60e5e68"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7cb08deb79c4385547f57d6bb2864e0f"

CFLAGS += " -O0 "

do_compile() {
    cp ${WORKDIR}/build.mk ${S}/mk/
    oe_runmake DESTDIR="${D}"
}

do_install() {
    oe_runmake install DESTDIR="${D}"
}
