/*
 * IGinX - the polystore system with high performance
 * Copyright (C) Tsinghua University
 * TSIGinX@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package cn.edu.tsinghua.iginx.s3;

import cn.edu.tsinghua.iginx.engine.physical.storage.HostPolicy;
import cn.edu.tsinghua.iginx.engine.physical.storage.StorageDriverDescriptor;
import java.io.IOException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Keeps the registered domain for services declaring virtual_style or preserve_host. */
public class S3DriverDescriptor implements StorageDriverDescriptor {

  private static final Logger LOGGER = LoggerFactory.getLogger(S3DriverDescriptor.class);

  @Override
  public HostPolicy hostPolicy(Map<String, String> options) {
    try {
      return S3Config.preservesHost(options) ? HostPolicy.PRESERVE_HOST : HostPolicy.RESOLVE_TO_IP;
    } catch (IOException | RuntimeException e) {
      // invalid params are reported when the storage is created
      LOGGER.warn("failed to read host policy of s3 params, resolve the host to IP", e);
      return HostPolicy.RESOLVE_TO_IP;
    }
  }
}
