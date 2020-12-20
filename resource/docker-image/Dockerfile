FROM centos:7
ENV container docker
RUN (cd /lib/systemd/system/sysinit.target.wants/; for i in *; do [ $i == \
systemd-tmpfiles-setup.service ] || rm -f $i; done); \
rm -f /lib/systemd/system/multi-user.target.wants/*;\
rm -f /etc/systemd/system/*.wants/*;\
rm -f /lib/systemd/system/local-fs.target.wants/*; \
rm -f /lib/systemd/system/sockets.target.wants/*udev*; \
rm -f /lib/systemd/system/sockets.target.wants/*initctl*; \
rm -f /lib/systemd/system/basic.target.wants/*;\
rm -f /lib/systemd/system/anaconda.target.wants/*;
COPY ../tools/oracle-database-preinstall-18c-1.0-1.el7.x86_64.rpm oracle-database-preinstall-18c-1.0-1.el7.x86_64.rpm
COPY oracle-database-xe-18c-1.0-1.x86_64.rpm oracle-database-xe-18c-1.0-1.x86_64.rpm
EXPOSE 1521 5500
WORKDIR ./
VOLUME [ "/sys/fs/cgroup" ]
CMD ["/usr/sbin/init"]