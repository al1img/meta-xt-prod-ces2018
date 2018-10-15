DESCRIPTION = "gopsutil: psutil for golang."

GO_IMPORT = "github.com/shirou/gopsutil"

inherit go

SRC_URI = "git://${GO_IMPORT};protocol=https;destsuffix=${PN}-${PV}/src/${GO_IMPORT}"
SRCREV = "77e5abb6f06f55ebb685c9af26bcd3c58082e702"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/LICENSE;md5=ed7522382cec5b7a6d6ebb8e30eed40e"
PTEST_ENABLED = ""

FILES_${PN} += "${GOBIN_FINAL}/*"

do_compile[noexec] = "1"
