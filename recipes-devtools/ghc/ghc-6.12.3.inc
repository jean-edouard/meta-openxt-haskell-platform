require ghc-common.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI = " \
    https://downloads.haskell.org/~ghc/${PV}/ghc-${PV}-src.tar.bz2 \
    file://build.mk \
    file://gcc-4.9-fomit-frame-pointer.patch \
    file://use-order-only-constraints-for-directories.patch \
    file://linux-host-os-need-pthread.patch \
    file://binutils-2.24-config-guard.patch \
    file://integer-simple-noinline-pragmas.patch \
    file://0001-fix-a-bug-introduced-in-1fb38442d3a55ac92795aa6c5ed4.patch \
    file://0002-Fix-a-retainer-profiling-segfault.patch \
    file://0003-Run-finalizers-after-updating-the-stable-pointer-tab.patch \
    file://0004-Fix-crash-with-large-objects-7919.patch \
    file://0005-Fix-an-arithmetic-overflow-bug-causing-crashes-with-.patch \
    file://0006-stdcall-x86-64.patch \
    file://0007-x64-more-stack.patch \
    file://0008-7919-large-objects.patch \
    file://0009-Fix-segfault-in-retainer-profiling-when-using-multiple-cores-5909.patch \
    file://compile-ghc-with-no-pie.patch \
    file://force-ghc-to-compile-code-with-no-pie.patch \
    file://rts-no-hidden.patch \
"
SRC_URI[md5sum] = "4c2663c2eff833d7b9f39ef770eefbd6"
SRC_URI[sha256sum] = "6cbdbe415011f2c7d15e4d850758d8d393f70617b88cb3237d2c602bb60e5e68"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7cb08deb79c4385547f57d6bb2864e0f"
do_compile() {
    cp ${WORKDIR}/build.mk ${S}/mk/
    oe_runmake DESTDIR="${D}"
}

do_install() {
    # There is a race somewhere triggered when installing utils/hsc2hs/template-hsc.h
    # Work-around until upgrade.
    install -m 0755 -d ${D}/${libdir}/${BPN}-${PV}
    oe_runmake install DESTDIR="${D}"
}
