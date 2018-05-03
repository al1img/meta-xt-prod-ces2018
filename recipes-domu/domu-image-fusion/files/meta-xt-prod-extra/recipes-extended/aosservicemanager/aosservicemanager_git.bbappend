GO_IMPORT = "gitpct.epam.com/epmd-aepr/aos_servicemanager"

SRCREV = "${AUTOREV}"
SRC_URI_append = "git://git@${GO_IMPORT}.git;protocol=ssh;destsuffix=${PN}-${PV}/src/${GO_IMPORT}"
