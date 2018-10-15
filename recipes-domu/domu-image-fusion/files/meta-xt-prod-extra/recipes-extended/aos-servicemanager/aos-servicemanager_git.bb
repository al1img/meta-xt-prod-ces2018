DESCRIPTION = "AOS Service Manager"

LICENSE = "CLOSED"

GO_IMPORT = "gitpct.epam.com/epmd-aepr/aos_servicemanager"

SRCREV = "${AUTOREV}"
SRC_URI = "git://git@${GO_IMPORT}.git;protocol=ssh;destsuffix=${PN}-${PV}/src/${GO_IMPORT};branch=master"

GO_CGO_ENABLED = "1"

inherit go

DEPENDS += "\
    golang.org-x-sys \
    github.com-gorilla-websocket \
    github.com-cavaliercoder-grab \
    github.com-coreos-go-systemd \
    github.com-godbus-dbus \
    github.com-mattn-go-sqlite3 \
    github.com-opencontainers-runtime-spec \
    github.com-sirupsen-logrus \
    github.com-streadway-amqp \
    github.com-shirou-gopsutil \
"

RDEPENDS_${PN} += "\
    aos-runc \
    aos-netns \
    wondershaper \
    openssl \
    ca-certificates \
    iptables \
    python3 \
    python3-compression \
    python3-crypt \
    python3-enum \
    python3-json \
    python3-misc \
    python3-selectors \
    python3-shell \
    python3-six \
    python3-threading \
    python3-websocket-client \
"

RDEPENDS_${PN}-dev += "bash"

FILES_${PN} += "${GOBIN_FINAL}/*"

SRC_URI_append = " \
    file://aos-servicemanager.service \
    file://aos_servicemanager.cfg \
    file://ipforwarding.conf \
"

inherit systemd

SYSTEMD_PACKAGES = " \
    ${PN} \
"

SYSTEMD_SERVICE_${PN} = "aos-servicemanager.service"

FILES_${PN} += " \
    ${systemd_system_unitdir}/*.service \
    ${base_prefix}/var/aos/servicemanager/aos_servicemanager.cfg \
    ${sysconfdir}/sysctl.d/*.conf \
"

do_install_append() {
    install -d ${D}${base_prefix}/var/aos/servicemanager
    install -m 0644 ${WORKDIR}/aos_servicemanager.cfg ${D}${base_prefix}/var/aos/servicemanager

    install -d ${D}${base_prefix}/var/aos/servicemanager/fcrypt

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/*.service ${D}${systemd_system_unitdir}

    install -d ${D}${sysconfdir}/sysctl.d
    install -m 0644 ${WORKDIR}/*.conf ${D}${sysconfdir}/sysctl.d
}

VISSERVER = "192.168.0.1    wwwivi"

pkg_postinst_${PN} () {
if ! grep -q '${VISSERVER}' $D/etc/hosts ; then
    echo '${VISSERVER}' >> $D/etc/hosts
fi
}
