DESCRIPTION = "BACnet open source protocol stack"
AUTHOR = "Steve Karg"
SECTION = "console"
LICENSE = "GPLv2"
PR = "r0"
LIC_FILES_CHKSUM = "file://license/gpl-2.txt;md5=3040850b26eed151876dcd4b841f5235"
DEPENDS = "virtual/kernel"

inherit pkgconfig

# The static library needs to be built before the demo applications are built.
# So, disable parallel make for this recipe to ensure all pieces build in the
# proper linear order.
PARALLEL_MAKE = ""

# Grab latest revision to fix build error for gateway demo
SRCNAME = "bacnet-stack-0-8-0"
SRC_URI = "svn://svn.code.sf.net/p/bacnet/code/branches/releases;module=${SRCNAME};rev=r2790;protocol=http \
        file://0001-Adjusted-makefiles-for-cross-compile.patch \
        "        

# Use this to build the specific tagged revision that matches the recipe
# version name
#SRCNAME = "${BP}"
#SRC_URI = "svn://svn.code.sf.net/p/bacnet/code/tags;module=${SRCNAME};rev=HEAD;protocol=http \
#        file://0001-Adjusted-makefiles-for-cross-compile.patch \
#        "        

WORKDIR = "${TMPDIR}/work/${MULTIMACH_TARGET_SYS}/${PN}"
S = "${WORKDIR}/${SRCNAME}"

FILES_${PN} = " \
    ${bindir}/bacarf \
    ${bindir}/bacawf \
    ${bindir}/bacdcc \
    ${bindir}/bacepics \
    ${bindir}/baciamr \
    ${bindir}/bacinitr \
    ${bindir}/bacrd \
    ${bindir}/bacrp \
    ${bindir}/bacrpd.sh \
    ${bindir}/bacrpm \
    ${bindir}/bacscov \
    ${bindir}/bacserv \
    ${bindir}/bacts \
    ${bindir}/bacucov \
    ${bindir}/bacupt \
    ${bindir}/bacwh \
    ${bindir}/bacwi \
    ${bindir}/bacwir \
    ${bindir}/bacwp \
    ${bindir}/bacgateway \
    ${bindir}/bvlc.sh \
    ${bindir}/mstpcap \
    ${bindir}/mstpcrc \
    ${datadir}/readme.txt \
    "
    
FILES_${PN}-staticdev = " \
    ${libdir}/libbacnet.a \
    "
    
# Use the LDFLAGS settings already set by OE
TARGET_CC_ARCH += "${LDFLAGS}"

do_compile () {
    # Build the static libary and the main demo applications
    make clean
    make all
    
    # Build the gateway demo, which means rebuilding the library
    make clean
    make gateway
}

do_compile_append_${PN} () {
    # Uncomment if you want to rebuild the documentation
    # doxygen BACnet-stack.doxyfile

    # Uncomment if you want to perform static analysis on the project
    # ./splint.sh
}

# Move all the binary files to /usr/bin
do_install () {
    install -d ${D}${bindir} ${D}${datadir} ${D}${libdir} ${D}${incdir} 
    install -m 0755 ${S}/bin/bacarf ${D}${bindir}
    install -m 0755 ${S}/bin/bacawf ${D}${bindir}
    install -m 0755 ${S}/bin/bacdcc ${D}${bindir}
    install -m 0755 ${S}/bin/bacepics ${D}${bindir}
    install -m 0755 ${S}/bin/baciamr ${D}${bindir}
    install -m 0755 ${S}/bin/bacinitr ${D}${bindir}
    install -m 0755 ${S}/bin/bacrd ${D}${bindir}
    install -m 0755 ${S}/bin/bacrp ${D}${bindir}
    install -m 0755 ${S}/bin/bacrpd.sh ${D}${bindir}/bacrpd.sh
    install -m 0755 ${S}/bin/bacrpm ${D}${bindir}
    install -m 0755 ${S}/bin/bacscov ${D}${bindir}
    install -m 0755 ${S}/bin/bacserv ${D}${bindir}
    install -m 0755 ${S}/bin/bacts ${D}${bindir}
    install -m 0755 ${S}/bin/bacucov ${D}${bindir}
    install -m 0755 ${S}/bin/bacupt ${D}${bindir}
    install -m 0755 ${S}/bin/bacwh ${D}${bindir}
    install -m 0755 ${S}/bin/bacwi ${D}${bindir}
    install -m 0755 ${S}/bin/bacwir ${D}${bindir}
    install -m 0755 ${S}/bin/bacwp ${D}${bindir}
    install -m 0755 ${S}/bin/bacgateway ${D}${bindir}
    install -m 0755 ${S}/bin/bvlc.sh ${D}${bindir}
    install -m 0755 ${S}/bin/mstpcap ${D}${bindir}
    install -m 0755 ${S}/bin/mstpcrc ${D}${bindir}
    install -m 0644 ${S}/bin/readme.txt ${D}${datadir}
    install -m 0644 ${S}/lib/libbacnet.a ${D}${libdir}
}
        
